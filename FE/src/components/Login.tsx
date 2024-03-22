// import "./LoginPage.css";
function Login() {
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

        <button type="button" className="mb-3">
          <img
            src="img/kakao-login-btn.png"
            alt="kakao_err"
            className="h-full w-full"
          />
        </button>
        <button type="button" className="">
          <img
            src="img/google-login-btn.png"
            alt="google_err"
            className="h-full w-full"
          />
        </button>
      </div>
    </>
  );
}

export default Login;
