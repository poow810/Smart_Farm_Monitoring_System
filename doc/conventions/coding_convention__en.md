# Coding Convention

> Referenced the Airbnb Coding Convention
 
## 0. Covered Range Of This Document
This project is driven by small groups of people, so each individual will be responsible for their own field-specific conventions and environments.
Therefore, this document does not cover those aspects.


## 1. Naming Convention
### 1.1.General
#### 1.1.1 Abbreviated forms are not allowed
```js
// We use 'numberOfDays' instead of 'numOfDays'.
let numberOfDays = 7;
```
```js
// Do not use 'temp'
let temp = 20; // 'temperature' or 'temporary' 
// Use instead
let temperature = 20;
```
#### 1.1.2 No more than four words
Try not to exceed four words.
If you need to go beyond that, you should agree with your teammates.
### 1.2. Variable
#### 1.2.1 use camelCase
```js
let thisIsCamelCase = "Camel Case";
```
#### 1.2.2 should not start with verb
```js
let receivedData = data.json();
```
### 1.3. Class and Component
#### 1.3.1 use PascalCase
```js
class MyClass { 
    // ...
}
```
#### 1.3.2 should not start with verb
```js
class receiveData (data) {
    //...
}
```
### 1.4 Function
#### 1.4.1 use camelCase
#### 1.4.2 should start with verb
```js
function getData (){
    //...
}
```
### 1.5 Boolean
#### 1.5.1 use `is` or `has`
`is--` or `has--`, `somethingFlag` is not good.
```js
let isOpen = false;
let hasChildren =true;
```
