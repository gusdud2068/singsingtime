## 📢 Git 사용 규칙
</br>

### 🚩 Branch 생성, 병합
</br>

💡 총 5개의 브랜치 사용
- feature - develop에서 feature 분기, 기능 추가 후 develop에 병합
- develop - feature, release를 병합하기 위해 사용
- release - develop에서 release 분기, 배포 준비 완료되면 master에 병합
- master - release 브랜치만 병합
- hotfix - master 브랜치에서 hotfix 브랜치 분기, 수정 후 다시 master에 병합
</br>

💡 feature 브랜치 생성 및 종료 과정

```bash
// feature 브랜치(feature/login)를 'develop' 브랜치('master' 브랜치에서 따는 것이 아니다!)에서 분기
git checkout -b feature/login develop

/* ~ 새로운 기능에 대한 작업 수행 ~ */

/* feature 브랜치에서 모든 작업이 끝나면 */
// 'develop' 브랜치로 이동한다.
git checkout develop
// 'develop' 브랜치에 feature/login 브랜치 내용을 병합(merge)한다.
# --no-ff 옵션: 아래에 추가 설명
git merge --no-ff feature/login
// -d 옵션: feature/login에 해당하는 브랜치를 삭제한다.
git branch -d feature/login
// 'develop' 브랜치를 원격 중앙 저장소에 올린다.
git push origin develop

```

</br></br>

### 🚩 Commit Convention
💡 Udacity Style (커밋 양식) 사용
```
커밋 메시지 양식

type: Subject

ex) 
docs: Update README.md
feat: Add 로그인 유효성 검사 기능
feat: Add Openvidu 컨트롤러 기능
fix: Modify 비밀번호 유효성 체크 기능
```
</br>

💡 type 규칙
- feat: 새로운 기능 추가
- fix: 버그 수정
- docs: 문서 수정
- style: 코드 포맷 변경, 세미콜론 누락, 코드 변경 없음
- refactor: 프로덕션 코드 리팩터링
- test: 테스트 추가, 테스트 코드 리팩터링, 프로덕션 코드 변경 없음
- chore: 빌드 테스크 업데이트, 패키지 매니저 환경설정, 프로덕션 코드 변경 없음
</br>

💡 Subject 규칙
- 동사(ex. Add, Update, Modify)로 시작
- 첫 글자 대문자
- 끝에 마침표 x


</br></br>

### 🚩 Jira Convention

💡 Epic - 큰 파트 생성
  - [단계] ~~~
  ex)
  [기획] 자료 탐색
  [기본기능] 로그인
  [필수기능] 플레이룸 사용
  [심화기능] 영상 커스터마이징
</br>

💡 Story - 사용자 행위 생성 (사용x)
  - 사용자는 ~~~할 수 있다
  ex)
  사용자는 회원가입할 수 있다
  사용자는 플레이룸을 생성할 수 있다.
</br>

💡 Task - 개발자 상세 업무 생성
  - Story point는 4시간 이하 할당
  - [포지션] ~~~ 정의/구현/설계/학습
  ex)
  [BE] 유저 엔티티 설계 
  [FE] 회원가입 기능 구현



