import { Link } from "react-router-dom";

const navigationMenus = [
  {
    icon: "/img/message-btn.png",
    url: "/chat",
    text: "채팅",
  },
  {
    icon: "/img/home-btn.png",
    url: "/",
    text: "홈",
  },
  {
    icon: "/img/mypage-btn.png",
    url: "/mypage",
    text: "마이",
  },
];

function BottomNavigationBar() {
  return (
    <>
      <div className="flex h-14 w-full border-t border-gray1 bg-white1 font-Gothic text-xxs">
        {navigationMenus.map((element) => (
          <Link
            key={element.text}
            to={element.url}
            className="flex flex-grow flex-col justify-center"
          >
            <button className="flex flex-col items-center space-y-1">
              <img src={element.icon} className="h-7 w-7" />
              <span>{element.text}</span>
            </button>
          </Link>
        ))}
      </div>
    </>
  );
}

export default BottomNavigationBar;
