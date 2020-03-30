package com.chen.mooc_manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.mooc_manager.cache.InMemoryCacheStore;
import com.chen.mooc_manager.cache.StringCacheStore;
import com.chen.mooc_manager.dao.CommentDao;
import com.chen.mooc_manager.dao.CourseDao;
import com.chen.mooc_manager.dao.SectionDao;
import com.chen.mooc_manager.dao.TeacherDao;
import com.chen.mooc_manager.model.*;
import com.chen.mooc_manager.service.CourseService;
import com.chen.mooc_manager.service.impl.MailServiceImpl;
import com.chen.mooc_manager.util.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MoocManagerApplicationTests {

	@Autowired
	CourseService courseService;

	@Resource
	CourseDao courseDao;

	@Resource
	SectionDao sectionDao;

	@Resource
	TeacherDao teacherDao;

	@Resource
	CommentDao commentDao;

	@Autowired
	MailServiceImpl mailServiceImpl;


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

	@Test
	public void courseServiceImpl_getShowDetail(){
		List<Section> sections = sectionDao.selectList(new QueryWrapper<Section>().lambda().eq(Section::getCourseId,"2"));
		log.info(sections.toString());

		log.info(Optional.ofNullable(teacherDao.selectById("2")).orElse(new Teacher()).toString());
		log.info(Optional.ofNullable(teacherDao.selectById("3")).orElse(new Teacher()).toString());

		log.info(sectionDao.selectList(new QueryWrapper<Section>().lambda().eq(Section::getCourseId,"21565")).toString());
	}

	@Test
	public void test_saveHtmlJson(){
			Comment comment = new Comment();
			comment.setSectionId(2);
			comment.setUserId(2);
			comment.setContent("哇，猴犀利");
			log.info(String.valueOf(commentDao.insert(comment)));
	}

	@Test
	public void test1(){

		StringCacheStore cacheStore = new InMemoryCacheStore();

		log.info("");
		Optional<String> code;
		Optional<String> code1;
		cacheStore.put("code","123456",2, TimeUnit.SECONDS);

		try {
			Thread.sleep(1996);
			code1 = cacheStore.get("code");
			code = cacheStore.get("code");

			code1.ifPresent(log::info);
			code.ifPresent(log::info);

			Thread.sleep(1);
			code = cacheStore.get("code");
			code.ifPresent(log::info);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2(){
		mailServiceImpl.sendTextMail("291203136@qq.com","验证码","上课咯");
	}
}
