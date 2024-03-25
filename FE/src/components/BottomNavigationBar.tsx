import { Link } from "react-router-dom";

function BottomNavigationBar() {
  return (
    <>
      <div className="fixed bottom-0 z-10 flex h-14 w-full border-t border-gray1 bg-white1 font-Gothic text-xxs ">
        <Link
          to="/new"
          className="flex flex-grow flex-col items-center justify-center space-y-1"
        >
          <button>
            <img src="/img/sell-btn.png" className="h-7 w-7" />
            <a>팝니다</a>
          </button>
        </Link>

        <Link
          to="/category"
          className="flex flex-grow flex-col items-center justify-center space-y-1"
        >
          <button className="flex flex-grow flex-col items-center justify-center space-y-1">
            <img src="/img/buy-btn.png" className="flex h-7 w-7" />
            <a>삽니다</a>
          </button>
        </Link>
        <Link
          to="/"
          className="flex flex-grow flex-col items-center justify-center space-y-1"
        >
          <button>
            <img src="/img/home-btn.png" className="h-6 w-7" />
            <a>홈</a>
          </button>
        </Link>
        <Link
          to="/chat"
          className="flex flex-grow flex-col items-center justify-center space-y-1"
        >
          <button>
            <img src="/img/message-btn.png" className="h-7 w-7" />
            <a>채팅</a>
          </button>
        </Link>
        <Link
          to="/login"
          className="flex flex-grow flex-col items-center justify-center space-y-1"
        >
          <button>
            <img src="/img/mypage-btn.png" className="h-7 w-7" />
            <a>마이</a>
          </button>
        </Link>
      </div>
    </>
  );
}

export default BottomNavigationBar;
