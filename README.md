# JAVA&Realm으로 만든 메모장 
# 자바 메모장
# 로이드 메모장


![image](https://user-images.githubusercontent.com/71119800/132815477-13af8c50-c73b-4e2f-b5ed-e9de34e53d62.png)



# MainActivity.java
<img src="https://user-images.githubusercontent.com/71119800/132938392-04e96f7f-c187-48a2-ac64-394dc0bf37b4.png"  width="250"> --><img src="https://user-images.githubusercontent.com/71119800/132938827-a1fbfcad-bf4e-4cc7-841e-11166b882ab9.png"  width="250">

- Application실행시 가장먼저 보이는 화면
- 하단의 플로팅 버튼을 터치하여 메모작성
- ----저장후----
- 롱터치를 통한 복사,삭제 기능지원
- 우측 복사버튼을 활용하여 간단복사

[코드보러가기](https://github.com/KimDong-Han/memo_java/blob/9855762609e657e8d17b30300a05f5151f344024/app/src/main/java/kr/ac/kumoh/s20131582/memo_java/MainActivity.java)


# MemoActivity.java
<img src="https://user-images.githubusercontent.com/71119800/132938408-fd7f8174-d756-49d5-a6d7-647d52b61989.png" width="250"><img src="https://user-images.githubusercontent.com/71119800/132938440-d0829746-7bd8-4207-9674-9c2a6704d24d.png"  width="250"><img src="https://user-images.githubusercontent.com/71119800/132938587-1ca5ee79-616a-4507-a79b-6b98098b8a1e.png"  width="250">

- 메모작성 화면
- 플로팅 버튼을 통한 사진 첨부 및 저장기능 지원
- 저장버튼이 아닌 Cancel(뒤로가기)버튼 입력시 저장/취소 여부 팝업


[코드보러가기](https://github.com/KimDong-Han/memo_java/blob/9855762609e657e8d17b30300a05f5151f344024/app/src/main/java/kr/ac/kumoh/s20131582/memo_java/MemoActivity.java)

# MemoModify.java
<img src="https://user-images.githubusercontent.com/71119800/132938732-29a7f464-b954-49e3-aa62-e726dcb51296.png" width="250">
- 기존의 메모를 수정,삭제 가능한 기능 지원
- 첨부된 사진을 클릭하여 확대하고 해당 상태에서 핀치줌 확대축소 지원


[코드보러가기](https://github.com/KimDong-Han/memo_java/blob/9855762609e657e8d17b30300a05f5151f344024/app/src/main/java/kr/ac/kumoh/s20131582/memo_java/MemoModify.java)



# Memo.java


- Realm에서 사용할 오브젝트 클래스(DB구조)
- 제목, 날짜, 내용,사진경로(URI)를 담을 오브젝트를 생성

[코드보러가기](https://github.com/KimDong-Han/memo_java/blob/1f65d91dbde90c7f93e73b9132bc06449e0e01a6/app/src/main/java/kr/ac/kumoh/s20131582/memo_java/Memo.java)

# MemoAdapter.java
[코드보러가기](https://github.com/KimDong-Han/memo_java/blob/1f65d91dbde90c7f93e73b9132bc06449e0e01a6/app/src/main/java/kr/ac/kumoh/s20131582/memo_java/MemoAdapter.java)

-recyclerview에 담기 위한 Adapter구현

## Application 구동을 위한 기타 class들

- [Migration.java](https://github.com/KimDong-Han/memo_java/blob/9855762609e657e8d17b30300a05f5151f344024/app/src/main/java/kr/ac/kumoh/s20131582/memo_java/Migration.java)
-> Realm DB 구조 변경에 따른 업데이트에 필요

- [Pop_XXX.java 파일들](https://github.com/KimDong-Han/memo_java/tree/master/app/src/main/java/kr/ac/kumoh/s20131582/memo_java)
-> 메모에 첨부된 이미지뷰,핀치줌 기능등 구현

- [XML파일들](https://github.com/KimDong-Han/memo_java/tree/master/app/src/main/res/layout)

# 다운받기(PlayStore)

 <a href="https://play.google.com/store/apps/details?id=kr.ac.kumoh.s20131582.memo_java" target = "_blank"><img src="https://user-images.githubusercontent.com/71119800/132939654-db623948-6cbe-46b8-8454-267d9494231d.png" width="250">

[Google Play](https://play.google.com/store/apps/details?id=kr.ac.kumoh.s20131582.memo_java)


# power by Realm
<a href="https://realm.io"><img src="https://user-images.githubusercontent.com/71119800/132815202-6f98cdfe-2766-46bb-a3af-2f0c088e1713.png"  width="250">
 
 [realm](https://realm.io)


