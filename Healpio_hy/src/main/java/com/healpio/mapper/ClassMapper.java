package com.healpio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.healpio.vo.ClassVO;
import com.healpio.vo.ExerciseVO;

public interface ClassMapper {

	public List<ExerciseVO> getExerciseList();
	public int insert(ClassVO classVO);
	public ClassVO getOne(String class_no);
	public int update(ClassVO classVO);
	public int delete(String class_no);
	public int scrap(@Param("class_no") String class_no, @Param("member_no") String member_no);
	public int cancelScrap(@Param("class_no") String class_no, @Param("member_no") String member_no);
	public int scrapYN(@Param("class_no") String class_no, @Param("member_no") String member_no);
	public int deleteScrap(String class_no);
	
}
