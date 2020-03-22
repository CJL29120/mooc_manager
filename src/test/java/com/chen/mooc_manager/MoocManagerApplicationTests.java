package com.chen.mooc_manager;

import com.chen.mooc_manager.dao.CourseDao;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.service.CourseService;
import com.chen.mooc_manager.util.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MoocManagerApplicationTests {

	@Autowired
	CourseService courseService;

	@Resource
	CourseDao courseDao;

	@Test
	public void contextLoads() {

		/*
		* CourseController ：
		* edit测试updateById参数更新依据
		* 测试结果updateById会根据course字段值是否为null和是否不同去判断此值是否更新
		* 当字段值不为null且字段值不同时才更新
		 */

		Course oldC = courseService.getById(34);
		log.info("oldC:"+oldC.toString());

		Course course = new Course();
		course.setId(34);
		course.setName("");
		log.info("course:"+course.toString());



		courseService.updateById(course);  //更新course字段不为空的字段值

		Course newC = courseService.getById(34);
		log.info("newC:"+newC.toString());
		if(oldC.getCoverUrl() != newC.getCoverUrl()) {
			log.info("course的全部字段都更新了");
		}else {
			log.info("仅仅更新了,字段值不为空的字段");
		}

		log.info("oldC:"+oldC.toString());
		log.info("newC:"+newC.toString());
	}

	@Test
	public void paramUtil(){
		ParamUtil util = new ParamUtil();
		Course course = new Course();
		course.setName("a3ds1 21sda  ");
		course.setDescription("\n   s\td   d f\r  ");
		course.setCoverUrl(" ka as asda  ");
		course.setShortIntro("");
		util.afterTrim(course);
		System.out.println(course.toString());
		System.out.println(course);
	}

	@Test
	public void urlEncode(){

		String s = "asdsa&";
		log.info(s.substring(0,s.length()-1));
	}

	@Test
	public void courseDao_selectWithCondition(){
		HashMap<String,String> map = new HashMap<>();
		map.put("type","0");
		map.put("classify_id","1");
		List<Course> courses = courseDao.selectWithCondition(map,"weight","1",1,9);
		courses.forEach(System.out::println);
		log.info(courses.size()+"");
	}
}
