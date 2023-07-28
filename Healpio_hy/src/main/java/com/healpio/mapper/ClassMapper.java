package com.healpio.mapper;

import java.util.List;

import com.healpio.vo.ClassVO;
import com.healpio.vo.ExerciseVO;

public interface ClassMapper {

	public List<ExerciseVO> getExerciseList();
	public int insert(ClassVO classVO);
	public ClassVO getOne(String class_no);
	public int update(ClassVO classVO);
	
}
