# Buckshot 룰렛




## 개요


> **‘카카오 클라우드 스쿨 in Jeju’를 진행하며 제작한 CLI 프로그램입니다.**
>
- **프로젝트 이름** : TCP 통신을 사용한 Buckshot Roulette
- **원작** : [Buckshot Roulette (steam)](https://store.steampowered.com/app/2835570/Buckshot_Roulette/?l=koreana)
- **프로젝트 개발 기간** : 2024.05.16 ~
- **개발 언어** : Java 21
- **개발 IDE** : IntelliJ IDEA CE

## 프로젝트 실행 방법

서버 실행
```
cd 5-haisely-java-cli/src/main/java
javac com/buckshot/NetworkMain.java
java com.buckshot.NetworkMain
```

클라이언트 실행
```
cd 5-haisely-java-cli/src/main/java
javac com/buckshot/ClientMain.java
java com.buckshot.ClientMain
```


## 코트 구조


```java
5-haisely-java-cli/
├── .idea/
│   └── (IDE configuration files)
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── buckshot/
│   │   │           ├── Core/
│   │   │           │   ├── GameManager.java
│   │   │           │   └── User.java
│   │   │           ├── Items/
│   │   │           │   ├── Beer.java
│   │   │           │   ├── Cigarette.java
│   │   │           │   ├── Handcuff.java
│   │   │           │   ├── Item.java
│   │   │           │   ├── Knife.java
│   │   │           │   ├── Magnifier.java
│   │   │           │   └── UserItem.java
│   │   │           └── Utils/
│   │   │               └── RandomUtils.java
│   │   └── resources/
│   │       └── (Resource files)
│   └── test/
│       ├── java/
│       │   └── (Test source files)
│       └── resources/
│           └── (Test resource files)
├── .DS_Store
├── .gitignore
├── README.md
├── build.gradle.kts
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

## 프로젝트 설명


- 플레이어 2명이 돌아가면서 자신 혹은 상대에게 총을 발사한다.
- 자신에게 발사할 총알이 공포탄인 경우 턴이 유지되고, 그 외의 경우 상대의 턴으로 넘어간다.
- 상대방의 체력이 0 이하가 되면 승리한다.

### 라운드

- 총에 3개~8개의 총알이 장전된다. (이 때, 공포탄과 실탄의 개수가 표기된다)
- 장전된 총알을 다 소모한 경우 라운드가 끝이난다.

### 사용자 턴

- 사용자는 자신의 턴에서 3가지 행동을 할 수 있다.
    - 아이템 사용
    - 자신에게 총을 쏘기
    - 적에게 총을 쏘기
- 아이템을 사용할 경우, 자신의 테이블에 있는 아이템 중 하나를 선택하여 사용할 수 있다.
- 자신에게 총을 쏜 경우, 공포탄이 발사되었을 때 턴이 넘어가지 않는다.
- 적에게 총을 쏜 경우, 무조건 상대에게 턴이 넘어간다.

## 프로젝트 플레이 방식

### 프로젝트 실행

- 게임 시작
    - 플레이어의 이름을 입력받는다. (Player 1, Player 2)
    - 플레이어의 이름을 출력한 뒤 라운드를 시작한다.
    - 플레이어의 피가 0 이하가 되거나, 10개의 라운드를 진행한 이후에도 게임이 끝나지 않은 경우 게임이 종료된다.
- 라운드 시작
    - 라운드 시작을 알린다. (Round 1)
    - 총에 랜덤하게 총알을 장착한다.
    - 장착한 총알의 실탄과 공포탄 수를 출력한다.
    - 플레이어 턴이 시작된다.
    - 총알을 다 소모한 경우, 해당 라운드를 종료하고 다음 라운드를 시작한다.

|게임 시작 화면 (플레이어 이름 입력)|라운드 시작 (총 장전, 아이템 분배)|
|:---:|:---:|
|![01](https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/b36d425d-5493-4603-99ea-f706f0d9aa47)|![02](https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/cfe5fc6f-69f6-495e-8455-ccd0e98f0aaf)|

### 플레이어 턴

- 플레이어는 다음 3가지 행동 중 하나를 선택할 수 있다.
    - 아이템 사용 (턴을 유지)
    - 자신에게 총을 쏘기 (공포탄일 경우 턴을 유지하고, 실탄일 경우 턴을 상대에게 넘김)
    - 적에게 총을 쏘기 (실탄 여부와 상관 없이, 턴이 상대에게 넘김)
  
|아이템 사용|자신에게 쏘기 (공포탄 발사)|적에게 쏘기 (실탄 발사)|
|:---:|:---:|:--:|
|![03](https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/8c77d8bc-49bb-41fd-8f7a-c8c349024546)|![04](https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/81bb9b72-9e49-4bc3-b21d-924e8f81ded1)|![05](https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/3c5097ac-ad97-4954-89cf-64e9614af0a4)|

### 아이템 사용
- 플레이어는 자신의 테이블에 있는 아이템 중 하나를 선택하여 사용할 수 있다.
    - 담배 : 자신의 체력을 1 올려준다. 최대 체력(6)일 경우 아이템만 소모한다.
    - 돋보기 : 현재 장전된 총알이 공포탄인지 실탄인지 알 수 있다.
    - 맥주 :장전된 탄알을 버리고, 공포탄인지 실탄인지 표기한다.
    - 수갑 : 상대방이 다음 턴에 행동할 수 없도록 막는다.
    - 식칼 : 총의 데미지를 2로 올린다.
  
|담배|돋보기|맥주|
|:---:|:---:|:--:|
|<img width="657" alt="스크린샷 2024-05-21 오후 4 27 27" src="https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/2cb45af4-5ec4-4b4b-817e-95dea84f0ad2">|<img width="657" alt="스크린샷 2024-05-21 오후 4 31 04" src="https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/4af623cd-5eb6-4ba6-a349-4a25e03ad29f">|<img width="657" alt="스크린샷 2024-05-21 오후 4 28 06" src="https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/9449b44d-4ff1-4db1-89ec-ff51cbe20670">|

|수갑|식칼|
|:---:|:---:|
|<img width="651" alt="스크린샷 2024-05-21 오후 4 28 53" src="https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/2a3f5420-3470-47ee-9871-2f59a00556e2">|<img width="661" alt="스크린샷 2024-05-21 오후 4 26 50" src="https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/45748e0f-ba66-4d83-baba-c4ab36eeaa77">|

### 게임 종료
- 플레이어 중 한 명의 체력이 0 이가 된 경우, 다른 플레이어의 승리를 출력하고 프로그램을 종료한다.
- 10개의 라운드가 진행된 후에도 끝나지 않은 경우, 무승부를 출력하고 프로그램을 종료한다.

|플레이어 우승|무승부|
|:---:|:---:|
|<img width="648" alt="스크린샷 2024-05-21 오후 4 45 04" src="https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/149955aa-2b37-43cb-98b1-6ddf63f8c988">|<img width="652" alt="스크린샷 2024-05-21 오후 4 44 13" src="https://github.com/100-hours-a-week/5-haisely-java-cli/assets/98401161/c085f882-1250-4f77-9f72-d7ce8d0b1b96">|

