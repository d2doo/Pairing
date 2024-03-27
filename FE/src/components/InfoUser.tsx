import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

function InfoUser() {
  return (
    <>
      <div className="mb-14 flex flex-col items-center border-y border-gray1 font-GothicLight text-sm">
        <div className="w-50 mt-8 flex items-center space-x-4 text-xs">
          <img
            src="/img/extra.png"
            alt="user_err"
            className="size-12 object-cover"
          />
          <div>
            <p>유저 이메일 들어올 곳~!</p>
            <div className="flex">
              <img
                src="/img/platinum.png"
                alt="pl_err"
                className="size-4 object-cover"
              />
              <p>유저 티어티어티어</p>
            </div>
          </div>
        </div>
        <div className="mt-8 flex w-full px-8">
          <div className="w-full">
            <p className="flex items-center font-Gothic text-xxs">닉네임</p>
            <div className="flex border-b-2">
              <Input
                placeholder="이곳에 입력하세요."
                className="test-xs"
              ></Input>
              <Button className="my-1 border border-gray1 bg-transparent">
                수정
              </Button>
            </div>
          </div>
        </div>
        <div className="mt-8 flex w-full px-8">
          <div className="w-full">
            <p className="flex items-center font-Gothic text-xxs">연락처</p>
            <div className="flex border-b-2">
              <Input
                placeholder="이곳에 입력하세요."
                className="test-xs"
              ></Input>
              <Button className="my-1 border border-gray1 bg-transparent">
                수정
              </Button>
            </div>
          </div>
        </div>
        <div className="mt-8 flex w-full px-8">
          <div className="w-1/2">
            <p className="flex items-center font-Gothic text-xxs">주소</p>
            <div className="flex border-b-2">
              <Input placeholder="클릭하세요." className="test-xs"></Input>
              <Button className="my-1 border border-gray1 bg-transparent">
                수정
              </Button>
            </div>
          </div>
        </div>
        {/* 다음 주소 가져오면 삭제할 부분 시작 */}
        <div className="w-full px-8">
          <div className="mt-2 w-full border-b-2">여기 나중에 삭제할거야</div>
          <div className="mt-2 w-full border-b-2">여기 삭제할꺼야</div>
        </div>
        {/* 삭제할 부분 끝 */}
        <div className="my-4 flex w-full justify-start px-8 underline-offset-4">
          <p className="text-left text-xxs text-gray1 underline">탈퇴하기</p>
        </div>
      </div>
    </>
  );
}

export default InfoUser;
