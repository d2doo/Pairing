import KakaoLoginButtonImage from '@/assets/images/kakao-login-btn.png';
import GoogleLoginButtonImage from '@/assets/images/google-login-btn.png';

function Login() {

  const kakaoLogin = () => {
    location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${
      import.meta.env.VITE_KAKAO_CLIENT_ID
    }&redirect_uri=${
      import.meta.env.VITE_REDIRECT_URI_BASE + '/kakao'
    }&response_type=code`;
  };

  return (
    <>
      <div className="justify-center">
        <div className="mb-9 grid">
          <a className="font-Chab text-logo text-white1 text-shadow-default">
            PAIRING
          </a>
          <a className="font-Gothic text-black1 text-shadow-md">
            pairing mine, pairing yours
          </a>
        </div>

        <button type="button" className="mb-3" onClick={kakaoLogin}>
          <img
            src={KakaoLoginButtonImage}
            alt="kakao_err"
            className="h-full w-full"
          />
        </button>
        <button type="button" className="">
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
