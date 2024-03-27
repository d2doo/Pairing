import { useNavigate } from "react-router-dom";

function Header() {
  const navigate = useNavigate();
  return (
    <>
      <div className="flex h-12 w-full items-center border-b border-gray1 px-4 font-GothicMedium text-base text-black1">
        <button
          onClick={() => {
            navigate(-1);
          }}
        >
          <img src="/img/back-btn.png" alt="back_err" className="h-4 w-auto" />
        </button>
        <p className="mx-3 flex items-center">페이지 이름이지롱</p>
      </div>
    </>
  );
}

export default Header;
