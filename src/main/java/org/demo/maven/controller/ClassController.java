package org.demo.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.demo.maven.model.Classes;
import org.demo.maven.model.Teacher;
import org.demo.maven.respository.ClassRespository;
import org.demo.maven.respository.TeacherRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONSerializer;

@Controller
public class ClassController {
	@Autowired
	ClassRespository classRespository;
	
	@Autowired
	TeacherRespository teacherRespository;
	
	@RequestMapping(value = "/class/addClass", method = RequestMethod.POST)
	@ResponseBody
    public String addClass(
			final String className, final String grade, final String mainTeacherId, final String militaryTeacherId, 
			final String politicsTeacherId, final String martialTeacherId, String scienceTeacherId,
			String artTeacherId, String medicineTeacherId, String literatureTeacherId)
    {
        // 数据库中添加一个用户，该步暂时不会刷新缓存,save()方法处理完毕后，数据依然在缓冲区未写入数据库
		Teacher mainTeacher = teacherRespository.findOne(Long.valueOf(mainTeacherId));
		Classes c = new Classes();
		c.setClassName(className);
		c.setGrade(grade);
		c.setMainTeacher(mainTeacher);
        // 数据库中添加一个用户，并立即刷新缓存并写入数据库
		classRespository.saveAndFlush(c);
    	Map<String,Classes> map=new HashMap<String, Classes>();
        map.put("class", c);
        String jsonString = JSONSerializer.toJSON(map).toString();
        return jsonString;
    }
	
	@RequestMapping(value = "/class/classes", method = RequestMethod.GET)
	@ResponseBody
    public String getUsers()
    {       
        List<Classes> classList = classRespository.findAll();
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("result", 1);
        map.put("classes", classList);
        String jsonString = JSONSerializer.toJSON(map).toString();
        return jsonString;
    }
}
