import { Link } from "react-router-dom";
import MyPage from "@/assets/images/mypage-btn.png";
import HOME from "@/assets/images/home-btn.png";
import Message from "@/assets/images/message-btn.png";

const navigationMenus = [
  {
    icon: Message,
    url: "/chat",
    text: "채팅",
  },
  {
    icon: HOME,
    url: "/",
    text: "홈",
  },
  {
    icon: MyPage,
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
