package com.gdu.cashbook;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
	// 검색해볼 것
	//servlet 요청처리
	//filter 요청전후처리
	//listener 이벤트 반응 처리
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CashbookApplication.class);
	}

}
