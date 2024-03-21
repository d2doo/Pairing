import { Button } from "./ui/button";
import { Input } from "@/components/ui/input";

function Chat() {
  return (
    <>
      {/* 채팅 내용 영역 */}
      <div className="m-1 font-GothicLight text-xs overflow-auto h-[calc(100vh-14rem-1rem)]">
        <div className="flex m-1 flex-col gap-2">
          <div className="self-end py-1 px-2 max-w-56 min-h-7 rounded-lg bg-blue3 flex items-center justify-end text-end">
            내가 적은 채팅 내용
          </div>

          <div className="flex items-center gap-2">
            <div className="self-start">
              <img
                src="/img/extra.png"
                alt="profile_err"
                className="object-cover w-10 h-10"
              />
            </div>
            <div className="max-w-56 px-2 py-1 rounded-lg bg-blue3">
              상대방이 적은 채팅 내용 이렇게 흘러넘치면 더 늘어나게 해놨지롱
              룰루랄라 φ(*￣0￣) 상대방이 적은 채팅 내용 이렇게 흘러넘치면 더
              늘어나게 해놨지롱 룰루랄라 φ(*￣0￣)
            </div>
          </div>
        </div>
      </div>

      {/* 입력 영역 */}
      <div className="fixed bottom-0 left-0 right-0 z-70 mb-14">
        <div className="flex justify-evenly items-center p-1">
          <img
            src="/img/add_pic_btn.png"
            alt="add_btn_err"
            className="size-4"
          />
          <div className="flex items-center p-1 border-2 border-black1 rounded-md w-56 h-7 font-Gothic text-xs">
            <Input placeholder="채팅 치는 공간 여기에 있어야함"></Input>
          </div>
          <Button className="h-7">전송</Button>
        </div>
      </div>
    </>
  );
}
export default Chat;
