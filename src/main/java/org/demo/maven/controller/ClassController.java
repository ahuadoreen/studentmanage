package org.demo.maven.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.demo.maven.model.*;
import org.demo.maven.respository.ClassCourseTeacherRepository;
import org.demo.maven.respository.ClassRepository;
import org.demo.maven.respository.GradeCourseRepository;
import org.demo.maven.respository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClassController {
	@Autowired
	ClassRepository classRepository;
	@Autowired
	private GradeCourseRepository gradeCourseRepository;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	ClassCourseTeacherRepository classCourseTeacherRepository;

	@RequestMapping(value = "/class/addClass", method = RequestMethod.POST)
	@ResponseBody
    public ResponseData addClass(
			final String className, final Integer grade, final String mainTeacherId)
    {
        // 数据库中添加一个用户，该步暂时不会刷新缓存,save()方法处理完毕后，数据依然在缓冲区未写入数据库
		Teacher mainTeacher = teacherRepository.findOne(Long.valueOf(mainTeacherId));
		Classes c = new Classes();
		c.setClassName(className);
		c.setGrade(grade);
		c.setMainTeacher(mainTeacher);
        // 数据库中添加一个用户，并立即刷新缓存并写入数据库
		classRepository.saveAndFlush(c);
		ResponseData responseData = ResponseData.ok();
		return responseData;
    }
	
	@RequestMapping(value = "/class/classes", method = RequestMethod.GET)
	@ResponseBody
    public ResponseData getClasses(final String name, final Integer index, final Integer size)
    {
		Sort sort = new Sort(Sort.Direction.ASC, "grade");
		Pageable pageable = new PageRequest(index, size, sort);
//		Classes classes = new Classes();
//		Teacher teacher = new Teacher();
//		teacher.setName(name);
//		classes.setMainTeacher(teacher);
//		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("main_teacher",ExampleMatcher.GenericPropertyMatchers.contains());
//		Example<Classes> example = Example.of(classes,exampleMatcher);
		Page<Classes> page = classRepository.findClassByMainTeacherName("%" + name + "%", pageable);
		int pageCount = page.getTotalPages();
		long total = page.getTotalElements();
		List<Classes> classesList = page.getContent();
		ResponseData responseData = ResponseData.ok();
		responseData.putDataValue("pageCount", pageCount);
		responseData.putDataValue("total", total);
		responseData.putDataValue("classes", classesList);
		return responseData;
    }

	@RequestMapping(value = "/class/editClass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData editClass(Long id, final String className, final Integer grade, final String mainTeacherId)
	{
		Teacher mainTeacher = teacherRepository.findOne(Long.valueOf(mainTeacherId));
		Classes c = classRepository.findOne(id);
		c.setClassName(className);
		c.setGrade(grade);
		c.setMainTeacher(mainTeacher);
		classRepository.saveAndFlush(c);
		ResponseData responseData = ResponseData.ok();
		return responseData;
	}

	@RequestMapping(value = "/class/deleteClass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteClass(Long id)
	{
		Classes classes = classRepository.findOne(id);
		classCourseTeacherRepository.deleteByClasses(classes);
		classRepository.delete(id);
		ResponseData responseData = ResponseData.ok();
		return responseData;
	}

	@RequestMapping(value = "/class/courseTeachers", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData getCourseTeacher(Long id, final Integer grade)
	{
		List<GradeCourse> gradeCoursesList = gradeCourseRepository.findGradeCoursesByGrade(grade);
		List<Map> mapList = new ArrayList<>();
		for(int i = 0;i<gradeCoursesList.size();i++){
			GradeCourse gradeCourse = gradeCoursesList.get(i);
			Map map = new HashMap();
			map.put("course", gradeCourse);
			ClassCourseTeacher classCourseTeacher = classCourseTeacherRepository.findClassCourseTeacherByClassesAndAndGradeCourse(id, gradeCourse.getId());
			if(classCourseTeacher != null){
				map.put("teacher", classCourseTeacher.getTeacher());
			}
			mapList.add(map);
		}
		ResponseData responseData = ResponseData.ok();
		responseData.putDataValue("courseTeachers", mapList);
		return responseData;
	}

	@RequestMapping(value = "/class/editCourseTeacher", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData editCourseTeacher(Long id, final Long gradeCourseId, final String teacherId)
	{
		Teacher teacher = teacherRepository.findOne(Long.valueOf(teacherId));
		GradeCourse gradeCourse = gradeCourseRepository.findOne(gradeCourseId);
		Classes classes = classRepository.findOne(id);
		classCourseTeacherRepository.deleteByClassesAndAndGradeCourse(classes, gradeCourse);
		ClassCourseTeacher classCourseTeacher = new ClassCourseTeacher();
		classCourseTeacher.setClasses(classes);
		classCourseTeacher.setGradeCourse(gradeCourse);
		classCourseTeacher.setTeacher(teacher);
		classCourseTeacherRepository.saveAndFlush(classCourseTeacher);
		ResponseData responseData = ResponseData.ok();
		return responseData;
	}
}
