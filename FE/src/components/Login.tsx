// import "./LoginPage.css";
function Login() {
  return (
    <>
      <div className="justify-center">
        <div className="grid mb-9">
          <a className="text-logo font-Chab text-white1 text-shadow-default">
            PAIRING
          </a>
          <a className="text-black1 font-Gothic text-shadow-md">
            pairing mine, pairing yours
          </a>
        </div>

        <button type="button" className="mb-3">
          <img
            src="img/kakao-login-btn.png"
            alt="kakao_err"
            className="w-full h-full"
          />
        </button>
        <button type="button" className="">
          <img
            src="img/google-login-btn.png"
            alt="google_err"
            className="w-full h-full"
          />
        </button>
      </div>
    </>
  );
}

export default Login;
