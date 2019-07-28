package org.demo.maven.controller;

import org.demo.maven.model.GradeCourse;
import org.demo.maven.model.ResponseData;
import org.demo.maven.model.Subject;
import org.demo.maven.respository.GradeCourseRepository;
import org.demo.maven.respository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GradeCourseController {
    @Autowired
    private GradeCourseRepository gradeCourseRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(value = "/gradeCourse/addGradeCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addGradeCourse(final Integer grade, final String[] subjectIds){
        for(int i=0;i<subjectIds.length;i++){
            GradeCourse gradeCourse = new GradeCourse();
            gradeCourse.setGrade(grade);
            Subject subject = subjectRepository.findOne(Long.valueOf(subjectIds[i]));
            gradeCourse.setSubject(subject);
            gradeCourseRepository.saveAndFlush(gradeCourse);
        }
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }

    @RequestMapping(value = "/gradeCourse/gradeCourses", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getGradeCourses()
    {
        List<Map> mapList = new ArrayList<>();
        for(int i = 1;i<3;i++){
            List<GradeCourse> gradeCoursesList = gradeCourseRepository.findGradeCoursesByGrade(i);
            Map map = new HashMap();
            map.put("grade", i);
            map.put("course", gradeCoursesList);
            mapList.add(map);
        }
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("gradeCourses", mapList);
        return responseData;
    }

    @RequestMapping(value = "/gradeCourse/editGradeCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editGradeCourse(final Integer grade, final String[] subjectIds)
    {
        gradeCourseRepository.deleteByGrade(grade);
        for(int i=0;i<subjectIds.length;i++){
            GradeCourse gradeCourse = new GradeCourse();
            gradeCourse.setGrade(grade);
            Subject subject = subjectRepository.findOne(Long.valueOf(subjectIds[i]));
            gradeCourse.setSubject(subject);
            gradeCourseRepository.saveAndFlush(gradeCourse);
        }
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }

    @RequestMapping(value = "/gradeCourse/deleteGradeCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteGradeCourse(final Integer grade)
    {
        gradeCourseRepository.deleteByGrade(grade);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }
}
