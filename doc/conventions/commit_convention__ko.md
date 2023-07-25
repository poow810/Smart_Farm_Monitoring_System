# Commit Convention

## 1. 전체 구조
```
type: subject

body

footer

```
##  2. Header 
### 2.1. header 구성 
    type : subject    
### 2.2. type
`feat :` 새로운 기능 추가
`fix :` 오작동 수정
`docs :` 문서 추가및 수정
`style :` 코드 형식, 포매팅
`refactor :` 코드 리팩토링
### 2.3 subject
마침표, 특수 기호 사용 불가
한글, 영어 병기 가능
영어로 작성시
- 시작은 동사로, 과거 시제 사용 금지
- 간결하고 요점적인 서술; 복잡하면 body로 넘기기
## 3. body
상세하고 편하게 작성해도 괜찮다.
단, 한줄에 72줄을 넘지 말것
## 4. footer
`type : 이슈 번호`
작성이 필수가 아님
이슈트레커 id를 기제
### 4.1. type
`fixes :` 이슈 수정중
`Resolves :` 이슈 해결
`Ref :` 참고할 이슈
`Related to :` 해당 커밋에 관련된 이슈번호 