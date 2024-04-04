# 🎎 Pairing

> 무선 이어폰을 한 쪽만 잃어버렸을 때도 활용 가능하도록 돕는 P2P 거래 프로젝트입니다.

## 목차

1. [기획 배경](#기획-배경)
2. [프로젝트 정보](#프로젝트-정보)  
   2-1. [프로젝트 주요 기능](#1-프로젝트-주요-기능)  
   2-2. [제작 기간](#2-제작-기간)  
   2-3. [참여 인원](#3-참여-인원)  
   2-4. [기술 스택](#4-기술-스택)  
   2-5. [개발 환경 및 빌드 방법](#5-개발-환경-및-빌드-방법)
3. [추가 정보](#추가-정보)  
   3-1. [깃 컨벤션](#git-컨벤션)  
   3-2. [데이터베이스 스키마](#데이터베이스-스키마)

<br/>

# 기획 배경

>
> - 이어폰 한 쪽을 잃어버렸을 때 어떻게 하나요?
> - 이어폰 한 쪽만 팔기 위해서 중고거래 사이트에 올려봤지만 잘 팔릴까요?

무선 이어폰을 잃어버렸을 때 애매하게 남은 나머지 파츠들을 재활용할 수 있도록 P2P 무선 이어폰 거래 사이트를 만들고 싶다 생각해 프로젝트를 시작하였습니다.

<br/>

# 프로젝트 정보

## 1. 프로젝트 주요 기능

### 메인 페이지
![mainpage](/uploads/9bcd06615ca3cdb25c2a5e112ce8c45f/mainpage.PNG)

### 로그인 & 마이페이지
![login & mypage](/uploads/685fc2c584e5fcc6a117f342b8d34c66/login.gif)

### 개별 상품 등록
![product](/uploads/4e8ea549d0fbf16ea7e646491e8403b6/product.gif)

### 조합 상품 등록
![combine](/uploads/8d6dc7892d41253151defd45d47c3784/last.gif)

### 개별 상품, 조합 상품 조회
![search](/uploads/a8388dba094fe18552a8d981875f05e2/search.gif)

### 거래 채팅
![search](/uploads/f8d7f09b916ebe5207b2e068f0aa1ea7/chat.PNG)

### 


### 프로젝트 인프라

![architecture](/uploads/d889c489f034e1eaf43059ab04fcbeb0/%EC%95%84%ED%82%A4_pairing_final.PNG)

## 2. 제작 기간

2024/02/26 ~ 2024/04/04

## 3. 참여 인원

| <img src="https://github.com/AMIVAYUN.png" width="120" height="120"/>  |   <img src="https://github.com/d2doo.png" width="120" height="120">   | <img src="https://github.com/dkdo1406.png" width="120" height="120"> | 
|:----------------------------------------------------------------------:|:---------------------------------------------------------------------:|:--------------------------------------------------------------------:| 
|                   [윤주석](https://github.com/AMIVAYUN)                   |                    [정지수](https://github.com/d2doo)                    |                  [김형중](https://github.com/dkdo1406)                  |
|                            BE & FE & INFRA                             |                                  FE                                   |                                  FE                                  |
|  <img src="https://github.com/kjy0349.png" width="120" height="120">   | <img src="https://github.com/Hyunnique.png" width="120" height="120"> | <img src="https://github.com/Hunnibs.png" width="120" height="120"/> | 
|                   [김제영](https://github.com/kjy0349)                    |                  [김대현](https://github.com/Hyunnique)                  |                  [이병헌](https://github.com/Hunnibs)                   |
|                            BE & FE & INFRA                             |                                BE & FE                                |                                  BE                                  |

<br/>

## 4. 기술 스택

### 공통 사용 기술

<img src="https://img.shields.io/badge/JWT-A100FF?style=for-the-badge">

### Front-End
<img src="https://img.shields.io/badge/typescript-3178C6?style=for-the-badge"> <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge"> <img src="https://img.shields.io/badge/zustand-592E42?style=for-the-badge"> <img src="https://img.shields.io/badge/tailwind_css-06B6D4?style=for-the-badge">

### Back-End

<img src="https://img.shields.io/badge/java_21-007396?style=for-the-badge"> <img src="https://img.shields.io/badge/Postgre-4479A1?style=for-the-badge"> <img src="https://img.shields.io/badge/springboot_3-6DB33F?style=for-the-badge"> <img src="https://img.shields.io/badge/JPA-FFFFCC?style=for-the-badge"> <img src="https://img.shields.io/badge/KAFKA-9e00ff?style=for-the-badge"> 

### INFRA

<img src="https://img.shields.io/badge/DOCKER-1d63ed?style=for-the-badge"> <img src="https://img.shields.io/badge/JENKINS-cc3631?style=for-the-badge"> <img src="https://img.shields.io/badge/aws_EC2-ed8233?style=for-the-badge">

### 협업

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white">  <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"> 

## 5. 개발 환경 및 빌드 방법

빌드 및 배포는 다음 파일 참조

[Infra](https://lab.ssafy.com/s10-blockchain-contract-sub2/S10P22A709/-/blob/main/exec/1.%20%ED%8F%AC%ED%8C%85%20%EB%A9%94%EB%89%B4%EC%96%BC/%ED%8F%AC%ED%8C%85%EB%A9%94%EB%89%B4%EC%96%BC.pdf?ref_type=heads)

# 추가 정보

Git 컨벤션
---

#### 브랜치 컨벤션

- 기본적으로 git-flow 따름

| main                | 최종 버전 관리 브랜치                     |
|---------------------|----------------------------------|
| dev                 | 개발 버전 관리 브랜치                     |
| fe                  | Front-End 개발 버전 관리 브랜치           |
| be                  | Back-End 개발 버전 관리 브랜치            |
| feature/{issue-num} | GitLab의 이슈에 해당하는 브랜치 (기능 개발 브랜치) |

- 개발 버전은 dev 브랜치에서 관리하되 규칙에 맞춰 dev 브랜치에서 분리하여 관리
    - fe : Front-End 개발 버전 브랜치
    - be-backend : Back-End 개발 버전 브랜치
- 각 feature 브랜치에서 Pull-Request를 통해 리뷰를 진행한 후, 1차적으로 분리된 dev 브랜치에 Merge 진행

#### 메시지 컨벤션

- 커밋 메시지는 아래의 양식을 따름
    - Merge 커밋의 경우 `[FE / BE / Infra]([GitLab 이슈 코드]) 상세 내용`의 양식으로 커밋

```
<타입>[적용 범위(선택사항)]: <설명>
[빈줄]
[본문 (선택사항)]
```

- 메시지의 타입은 아래의 유형에서 하나를 선택하여 사용

| 헤더 명     | 내용                            |
|----------|-------------------------------|
| feat     | 새로운 기능 추가                     |
| fix      | 버그 수정                         |
| chore    | 그 외 자잘한 수정 (프로젝트 설정 등)        |
| refactor | 코드 리팩토링                       |
| test     | 테스트 관련 코드                     |
| docs     | 문서 관련                         |
| style    | 코드 스타일 변경 (포매팅 수정, 들여쓰기 추가 등) |
| build    | 빌드 관련 파일 수정                   |

Erd Diagram
---
![ERD](/uploads/b54c8851cdc80de97583b181392f655e/pairing_erd.png)

