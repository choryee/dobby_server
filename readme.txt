
도비 마지막날. 23.12.04
로그인 ooo/1112 <--로그인 안 되면, 웹페이지 오른쪽 상단에 회원가입하고 하라.

내 로컬의 dev의 웹, 서버에는 콘솔로그 모두 지운 것, push origin dev했음.
--------------------

밑 내 브랜치는, 로컬의 dev와 다름.
서버 내 브랜치 - feature/#5
웹 내 브랜치 - feature/#22

서버구동은 위 DobbyApplication를 그냥 삼각형클릭으로 구동.
웹은 npm run serve

주소는
localhost:3000

vue화면의 컴포넌트를 사용시, 컨트롤러의 리턴을 CommonRespose.java형태로 리턴해야.
콘솔창에 스케쥴러 돔. <-- 귀찮으면, 스케줄러 파일 2개 찾아서, 주석처리하면 안 돔.
DayoffSyncScheduler.java CaldavSyncScheduler.java

사용될 sql은 왼쪽 dobby.sql 파일에 저장해놈.

# DB setting <--회사 db서버를 사용하고 싶을때(테이블명 바꾸어나서 안 됨. 240222), 아니면 밑을 localhost로 바꾸면 됨.
mariadb

dobby.jdbc.driver-class-name=org.mariadb.jdbc.Driver
dobby.jdbc.jdbc-url=jdbc:mariadb://211.215.19.105:23306/dobbyDB
dobby.jdbc.username=escaped
dobby.jdbc.password=gbrk3slek!
-------
dobby.jdbc.driver-class-name=org.mariadb.jdbc.Driver
dobby.jdbc.jdbc-url=jdbc:mariadb://127.0.0.1/3306/dobbyDB
dobby.jdbc.username=root
dobby.jdbc.password=1234



config/SecurityConfig.java
                        .anyRequest().permitAll() //모두 허용 <-- 이거해서, 현재 시큐리티에서 모두 허용됨. 걸러면, 밑 주석해제해야.
                    );
//                        .authorizeRequests()
//                        .antMatchers("/api/v1/users/join","/login", "/logout").permitAll()
//                        .anyRequest().authenticated();

