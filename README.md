

# Instargram clon ## Backend

인스타그램 클론 

**개발기간**
2022.08.19-2022.08.25

## 👥팀원소개

- BE: 박종익, 이길종, 하지혜
- FE: 박준기, 하병노

## ⚒️기술스택

- **백엔드**
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white"> 


<img src="[https://img.shields.io/badge/Spring](https://img.shields.io/badge/Spring) Security-6DB33F?style=flat&logo=Spring Security&logoColor=white"/> 
<img src="[https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white)"/> <img src="[https://img.shields.io/badge/JWT-000000?style=flat&logo=JWT&logoColor=white](https://img.shields.io/badge/JWT-000000?style=flat&logo=JWT&logoColor=white)"/> <img src="[https://img.shields.io/badge/Gradle-02303A?style=flat&logo=Gradle&logoColor=white](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=Gradle&logoColor=white)"/> <img src="[https://img.shields.io/badge/amazon](https://img.shields.io/badge/amazon) s3-569A31?flat&logo=Gradle&logo=amazons3&logoColor=green">
- **프론트엔드**
<img src="[https://img.shields.io/badge/html5-E34F26?style=flat&logo=Gradle&logo=html5&logoColor=white](https://img.shields.io/badge/html5-E34F26?style=flat&logo=Gradle&logo=html5&logoColor=white)"/> <img src="[https://img.shields.io/badge/css-1572B6?style=flat&logo=css3&logo=Gradle&logoColor=white](https://img.shields.io/badge/css-1572B6?style=flat&logo=css3&logo=Gradle&logoColor=white)"/> <img src="[https://img.shields.io/badge/javascript-F7DF1E?style=flat&logo=Gradle&logo=javascript&logoColor=black](https://img.shields.io/badge/javascript-F7DF1E?style=flat&logo=Gradle&logo=javascript&logoColor=black)"/> <img src="[https://img.shields.io/badge/react-61DAFB?style=flat&logo=react&logo=Gradle&logoColor=black](https://img.shields.io/badge/react-61DAFB?style=flat&logo=react&logo=Gradle&logoColor=black)"/> <img src="[https://img.shields.io/badge/styled](https://img.shields.io/badge/styled) components-DB7093?style=flat&logo=Gradle&logo=styledcomponents&logoColor=pink"/> <img src="[https://img.shields.io/badge/react](https://img.shields.io/badge/react) query-61DAFB?style=flat&logo=Gradle&logo=reactquery&logoColor=FF4154"/> <img src="[https://img.shields.io/badge/amazon](https://img.shields.io/badge/amazon) s3-569A31?style=flat&logo=amazons3&logoColor=green">

## 🌟핵심기능🌟

```
1. 회원가입/로그인
- email, 전화번호, 닉네임을 세개 다 아이디로 사용할 수 있음 

2. 게시글, 댓글 CRUD 
게시글, 댓글 목록 조회, 등록, 수정, 삭제, 상세조회

3. 다중 이미지 업로드

4. 마이 프로필 페이지   

5.(백엔드) 좋아요, 대댓글, RefreshToken, 소셜로그인 

```

## 🎥데모영상

**[https://youtu.be/3AoSzkpZolQ](https://youtu.be/3AoSzkpZolQ)**

## 🏀트러블 슈팅 ⛹️

 ### 1 **. JPA N+1 쿼리 성능개선**
```
  1. 문제상황
    - 게시글 전체 조회시 60개 가량의 쿼리가 발생.
  2. 시도한 방법
    - JPQL fetchJoin 사용 : member를 fetch Join 하여 쿼리 수를 줄였지만 여전히 코멘트와 이미지에서 N+1문제 발현 (실패 )
    - commentRepository에 직접적인 쿼리를 부르는 라인을 수정 (그럼에도 지연 로딩으로 인하여 N+1문제 여전히 발생) (실패 )
    - N+1 문제를 해결하기 위해 세 요소를 다 fetch Join으로 엮음(MultipleBagFetchException발생) (실패 )
    - JPQL사용하여 ManyToOne인 Member와 OneToMany의 CommentList를 fetchJoin으로 엮어 쿼리 수 최소화, 여전히 imgUrlList(5번) (실패 )
    - imgUrlList를 Set으로 바꾸고 member, imgUrlSet, CommentList 모두 fetchJoin으로 묶어봄 => Comment가 달리지 않은 게시글이 조회되지 않음(실패)
  3. 해결한 방법
    - 게시글에 필수적으로 들어가는 member 와 imgUrlList를 fetch Join으로 묶고 commentList를 left join fetch로 묶어서 시도.
    - member의 경우 ManyToOne관계기에 MultipleBagFetchException에서 자유롭고 imgUrlList의 경우 중복문제를 처리하기 위해 Set변수로 변경.
    - comment가 달리지 않은 게시물도 조회해야하기에 commentList는 left join fetch로 시도

⇒ 1번의 쿼리 만으로 전체 게시물, 좋아요 수로 댓글 두개 조회가 모두 구현 완료
```


### 2. PATCH

```
1. 문제상황
    - 업데이트에서 PUT 대신 PATCH를 썼을때 일부 변수만 수정하고 싶은 경우에 DTO에는 있지만 request에는 없는 변수는 null이 되는 문제
2. 시도한 방법
    - null체크를 서비스에서 각각에 해주는 방법을 사용하려했으나 Dto의 변수가 n개인 경우 추가적으로 2^(n)번의 처리가 필요하여 dto 그대로 request해야만 처리가 되는 PUT으로 돌아감
    - 처리의 예시
    - 서비스
    - 엔티티     
3. 해결방법
    - 실제로 업데이트가 이루어지는 엔티티에서 각각의 변수에 대해 null체크를 함으로써 어떤 형태로 request가 들어와도 처리 가능
```
    

### 3. JWT Refresh Token  기능 구현
```
    1. 문제상황
        - RefreshToken을 활용하지 못함
    2. 구현 방법
      1. RefreshToken을 TokenProvider에 위치한 토큰 유효성 검사를 한다.
      2. Access-Token-Expire-Time을 헤더로 받아와 저장되어있는 Date함수의 long값을 받아와 현재 시간과 비교한다. 
         만약 현재 시간이 만료 시간보다 이전이면 “INVALID-TOKEN” 에러 코드를 보낸다.
      3. 헤더로 들어온 “REFRESH-TOKEN”에 저장된 멤버 정보로 발급된 “REFRESH-TOKEN”이 있는지 확인하고 다를시 에러코드를 보낸다.
      4. 헤더로 들어온 “REFRESH-TOKEN”이 DB에 저장된 “REFRESH-TOKEN”과 같은지 확인하고 다를시 에러코드를 보낸다.
      5. 위의 예외처리를 다 거친 경우, “AUTHORIZATION”과 “Access-Token-Expire-Time”을 헤더값으로 반환한다.
      ```
      
      
 ### 추가하고 싶은 기능 
   * 태그 기능 
   * 팔로우 기능
   * DM 기능
   * 알람 기능
   * 프로필 수정, 사진 삭제
   * 그외..
    
    
    
    ![header](https://capsule-render.vercel.app/api?text=4조화이팅!&fontColor=d6ace6)


