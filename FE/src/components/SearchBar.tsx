import { useRef, useState } from "react";
import { Input } from "@/components/ui/input";

function SearchBar() {
  // 폼을 참조하기 위한 ref 생성
  const formRef = useRef<HTMLFormElement>(null);
  const [inputText, setInputText] = useState("");

  // 검색 버튼(이미지) 클릭 시 폼을 제출하는 함수
  const handleSubmit = () => {
    // 폼의 submit 이벤트를 프로그래매틱하게 트리거
    formRef.current?.submit();
  };

  // 실제 검색 로직을 수행하는 함수
  const handleSearch = (event: React.FormEvent) => {
    event.preventDefault(); // 폼의 기본 제출 동작을 방지
    // 여기에 검색 로직을 구현, 예: console.log(event.target.searchInput.value);
    console.log("검색어: ", inputText);
  };

  return (
    <>
      <nav className="mx-auto flex h-14 w-full items-center justify-around">
        <div className="my-6 flex items-center justify-center">
          <img src="img/favicon-btn.png" alt="favicon_err" />
        </div>
        {/* form 태그 추가 및 ref, onSubmit 이벤트 핸들러 연결 */}
        <form
          ref={formRef}
          onSubmit={handleSearch}
          className="box-border flex w-2/3 place-content-around items-center rounded-full border-2 border-blue1 bg-white1 bg-opacity-70 px-4"
        >
          <Input
            name="searchInput" // 폼 제출 시 값을 참조하기 위한 name 속성 추가
            type="search"
            className="box-border h-11 flex-grow border-none font-GothicMedium text-xs"
            placeholder="찾고있는 물건을 입력해보세요."
            value={inputText}
            onChange={(e) => {
              setInputText(e.target.value);
            }}
          />
          <img
            src="img/search-btn.png"
            alt="search_err"
            className="w-5"
            onClick={handleSubmit} // 이미지 클릭 시 handleSubmit 함수 호출
          />
        </form>
        <img src="img/notification-btn.png" alt="noti_err" className="size-7" />
      </nav>
    </>
  );
}

export default SearchBar;
