# 코딩 규칙
> 에어비앤비 코딩 켄벤션 참고
## 0. 본 문서의 적용 범위
이 프로젝트는 소수의 인원이 진행하므로, 분야마다 한 사람씩 맡아서 합니다.
따라서 본 문서는 분야내에서의 컨벤션과 환경에 대해서는 다루지 않습니다.

## 1. 명명 규칙
### 1.1. 일반
#### 1.1.1 축약된 형식 허용되지 않음
```js
// 'numOfDays' 대신 'numberOfDays'를 권장
let numberOfDays = 7;
```
```js
// 'temp'를 사용하지 말것
let temp = 20; // 'temperature' or 'temporary' 
let temperature = 20;
```
#### 1.1.2 네 단어 이하로 제한
네 단어를 넘지 않도록 하세요.
그 이상을 사용해야 한다면 팀원들의 동의를 구해야 합니다.
### 1.2. 변수
#### 1.2.1 camelCase 사용
```js
let thisIsCamelCase = "camelCase";
```
#### 1.2.2 동사로 시작하지 않음
```js
let receivedData = data.json();
```
### 1.3. 클래스 및 컴포넌트
#### 1.3.1 PascalCase 사용
```js
class MyClass { 
    // ...
}
```
#### 1.3.2 동사로 시작하지 않음.
```js
class receiveData (data) {
    //...
}
```
### 1.4 함수
#### 1.4.1 camelCase 사용
#### 1.4.2 동사로 시작함
```js
함수 getData (){
    //...
}
```
### 1.5 논리형
#### 1.5.1 `is` 또는 `has` 사용
`is--` 또는 `has--`, `somethingFlag`는 권장 되지 않습니다.
```js
let isOpen = false;
let hasChildren =true;
```