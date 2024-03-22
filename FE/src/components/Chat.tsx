import { Button } from "./ui/button";
import { Input } from "@/components/ui/input";

function Chat() {
  return (
    <>
      {/* 채팅 내용 영역 */}
      <div className="m-1 h-[calc(100vh-14rem-1rem)] overflow-auto font-GothicLight text-xs">
        <div className="m-1 flex flex-col gap-2">
          <div className="flex min-h-7 max-w-56 items-center justify-end self-end rounded-lg bg-blue3 px-2 py-1 text-end">
            내가 적은 채팅 내용
          </div>

          <div className="flex items-center gap-2">
            <div className="self-start">
              <img
                src="/img/extra.png"
                alt="profile_err"
                className="h-10 w-10 object-cover"
              />
            </div>
            <div className="max-w-56 rounded-lg bg-blue3 px-2 py-1">
              상대방이 적은 채팅 내용 이렇게 흘러넘치면 더 늘어나게 해놨지롱
              룰루랄라 φ(*￣0￣) 상대방이 적은 채팅 내용 이렇게 흘러넘치면 더
              늘어나게 해놨지롱 룰루랄라 φ(*￣0￣)
            </div>
          </div>
        </div>
      </div>

      {/* 입력 영역 */}
      <div className="z-70 fixed bottom-0 left-0 right-0 mb-14">
        <div className="flex items-center justify-evenly p-1">
          <img
            src="/img/add_pic_btn.png"
            alt="add_btn_err"
            className="size-4"
          />
          <div className="flex h-7 w-56 items-center rounded-md border-2 border-black1 p-1 font-Gothic text-xs">
            <Input placeholder="채팅 치는 공간 여기에 있어야함"></Input>
          </div>
          <Button className="h-7">전송</Button>
        </div>
      </div>
    </>
  );
}
export default Chat;
