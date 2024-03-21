import { Link } from "react-router-dom";

function BottomNavigationBar() {
  return (
    <>
      <div className="fixed flex w-full h-14 z-10 bottom-0 font-Gothic text-xxs border-t border-gray1 bg-white1">
        <button className="flex flex-col space-y-1 items-center justify-center flex-grow">
          <img src="/img/sell-btn.png" className="w-7 h-7" />
          <a>팝니다</a>
        </button>
        <button className="flex flex-col space-y-1 items-center justify-center flex-grow">
          <img src="/img/buy-btn.png" className="flex w-7 h-7" />
          <a>삽니다</a>
        </button>
        <Link
          to="/"
          className="flex flex-col space-y-1 items-center justify-center flex-grow"
        >
          <button>
            <img src="/img/home-btn.png" className="w-7 h-6" />
            <a>홈</a>
          </button>
        </Link>
        <Link
          to="/chat"
          className="flex flex-col space-y-1 items-center justify-center flex-grow"
        >
          <button>
            <img src="/img/message-btn.png" className="w-7 h-7" />
            <a>채팅</a>
          </button>
        </Link>
        <Link
          to="/login"
          className="flex flex-col space-y-1 items-center justify-center flex-grow"
        >
          <button>
            <img src="/img/mypage-btn.png" className="w-7 h-7" />
            <a>마이</a>
          </button>
        </Link>
      </div>
    </>
  );
}

export default BottomNavigationBar;
