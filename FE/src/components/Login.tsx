import KakaoLoginButtonImage from "@/assets/images/kakao-login-btn.png";
import GoogleLoginButtonImage from "@/assets/images/google-login-btn.png";
import { Link } from "react-router-dom";

function Login() {
  const kakaoLogin = () => {
    location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${
      import.meta.env.VITE_KAKAO_CLIENT_ID
    }&redirect_uri=${
      import.meta.env.VITE_REDIRECT_URI_BASE + "/kakao"
    }&response_type=code`;
  };

  const googleLogin = () => {
    location.href = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${
      import.meta.env.VITE_GOOGLE_CLIENT_ID
    }&redirect_uri=${
      import.meta.env.VITE_REDIRECT_URI_BASE + "/google"
    }&response_type=code&scope=openid%20profile%20email`;
  };

  return (
    <>
      <div className="justify-center">
        <Link to={"/"} className="mb-9 grid">
          <p className="font-Gothic text-black1 text-shadow-md">
            pairing your way!
          </p>
          <p className="font-Chab text-logo text-white1 text-shadow-default">
            PAIRING
          </p>
        </Link>

        <button type="button" className="mb-3" onClick={kakaoLogin}>
          <img
            src={KakaoLoginButtonImage}
            alt="kakao_err"
            className="h-full w-full"
          />
        </button>
        <button type="button" className="" onClick={googleLogin}>
          <img
            src={GoogleLoginButtonImage}
            alt="google_err"
            className="h-full w-full"
          />
        </button>
      </div>
    </>
  );
}

export default Login;
