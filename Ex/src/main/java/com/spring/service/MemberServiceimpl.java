package com.spring.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.mapper.MemberMapper;
import com.spring.vo.Member;

@Service
public class MemberServiceimpl implements MemberService {

	@Autowired
	MemberMapper mapper;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	ApiExamMemberProfile apiExam;

	@Override
	public Member login(Member paramMember) {
		Member member = mapper.login(paramMember);
		if(member!=null) {			
			// 사용자가 입력한 비밀번호와 DB에 암호화되어 저장된 비밀번호가 일치하는지 확인
			if(encoder.matches(paramMember.getPw(), member.getPw())) {
				System.out.println("------------------- 1 " + member);
				
				// Member 객체의 role 필드에 Memberrole 테이블에서 조회한 role을 저장
				member.setRole(mapper.getMemberRole(member.getId()));
				return member;
			}
		}
		return null;
	}

	@Override
	public int insert(Member member) {
		member.setPw(encoder.encode(member.getPw()));
		return mapper.insert(member);
	}

	@Override
	public int idCheck(Member member) {
		return mapper.idCheck(member);
	}
	
	@Override
	public void naverLogin(HttpServletRequest request, Model model) {
		try {
			// callback 처리 -> access_token
			Map<String, String> tokens = getToken(request);		
			String access_token = tokens.get("access_token");
			
			// access_token -> 사용자 정보 조회
			Map<String, Object> responseBody = apiExam.getMemberProfile(access_token);
			
			Map<String, String> response = (Map<String, String>) responseBody.get("response");
			
			model.addAttribute("id", response.get("id"));
			model.addAttribute("name", response.get("name"));
			model.addAttribute("email", response.get("email"));
			model.addAttribute("mobile", response.get("mobile"));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public Map<String, String> getToken(HttpServletRequest request) throws Exception{
		System.out.println("---------getToken---------");
		String clientId = "0NtlLkL33aCmf_EpKXin";//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "dnbZLL6eoN";//애플리케이션 클라이언트 시크릿값";
	    String code = request.getParameter("code");
	    String state = request.getParameter("state");
	    try {
		    String redirectURI = URLEncoder.encode("http://localhost:8080/login/naver_callback", "UTF-8");
		    String apiURL;
		    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		    apiURL += "client_id=" + clientId;
		    apiURL += "&client_secret=" + clientSecret;
		    apiURL += "&redirect_uri=" + redirectURI;
		    apiURL += "&code=" + code;
		    apiURL += "&state=" + state;
		    String access_token = "";
		    String refresh_token = "";
		    System.out.println("apiURL="+apiURL);
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode="+responseCode);
			if(responseCode==200) { // 정상 호출
			  br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
			  br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
			  res.append(inputLine);
			}
			br.close();
			if(responseCode==200) {
				System.out.println(res.toString()); // 토큰 출력  // JSON 형식
				Map<String, String>	map = new HashMap<String, String>();
				// ObjectMapper : Java 객체와 JSON 간 직렬화, 역직렬화  (Jackson 라이브러리에서 제공)
				ObjectMapper objectMapper = new ObjectMapper();
				// JSON 문자열을 Map 객체로 파싱
				map = objectMapper.readValue(res.toString(), Map.class);
				return map;
			} else {
				throw new Exception("callback 반환 코드: " + responseCode);
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception("callback 처리 중 오류가 발생하였습니다.");
		}
	}
	
}
