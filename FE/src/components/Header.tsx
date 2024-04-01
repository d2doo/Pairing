import { useNavigate } from "react-router-dom";
import BackBtn from "@/assets/images/back-btn.png";
import Notification from "./Notification";

interface Props {
  title: string;
  prev: string;
}

const Header: React.FC<Props> = ({ title, prev }) => {
  const navigate = useNavigate();

  return (
    <div className="flex h-12 w-full items-center justify-between border-b border-gray-200 px-4 font-GothicMedium text-black">
      <div className="flex h-12 w-full items-center">
        {prev && (
          <button
            onClick={() => {
              navigate(prev);
            }}
            className="mr-3"
          >
            <img src={BackBtn} alt="뒤로 가기" className="h-4 w-auto" />
          </button>
        )}
        <p className="flex items-center">{title}</p>
      </div>
      <div>
        <Notification />
      </div>
    </div>
  );
};

export default Header;
