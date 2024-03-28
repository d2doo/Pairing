import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
    DialogFooter,
    DialogClose,
  } from "@/components/ui/dialog";
  import { ChatImageUploader } from "./ChatImageUploader";
  import { Button } from "./ui/button";
  import { AxiosResponse } from "axios";
  import { useEffect, useState } from "react";
  import { setDefaultAutoSelectFamilyAttemptTimeout } from "net";
  
  function ChatImageDialog() {
    const [url, setUrl] = useState<string>();
    const [fileId, setFileId] = useState<number>();
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false); // 모달 상태 추가
    const [isPlusClick, setIsPlusClick] = useState<boolean>(false); // 모달 상태 추가
  
    const clearFile = () => {
      // 파일 선택 처리 로직...
      // alert();
      if (!isPlusClick) {
        console.log("파일 삭제");
        return true;
      }
      console.log("아무것도 아님");
      return false;
      // setIsModalOpen(true); // Dialog를 열기 위한 상태 업데이트
      // return true;
    };
    const handleModalClose = (send: boolean) => {
      if (send) {
        console.log("파일 전송");
      } else {
        console.log("창만 끈다.");
      }
  
      setIsPlusClick(false);
      setIsModalOpen(false);
    };
    useEffect(() => {
      if (!isPlusClick) {
        clearFile;
      }
      console.log("isButtonClick" + " " + isPlusClick);
    }, [isPlusClick]);
  
    useEffect(() => {
      if (isModalOpen) {
        setIsPlusClick(false);
      }
      console.log("isModalOpen" + " " + isModalOpen);
    }, [isModalOpen]);
  
    return (
      <>
        <div>
          <ChatImageUploader
            className="hidden"
            onUploadRequested={(preview: string) => {
              setUrl(preview);
              setIsModalOpen(true); // 업로드 완료 후 모달 상태 변경
              setIsPlusClick(false);
            }}
            onUploadComplete={(response) => {
              setFileId(response.data.fileId);
              setIsPlusClick(false); // 파일 업로드가 완료된 후 상태를 false로 재설정
            }}
            clear={clearFile()}
            open={isPlusClick}
          />
  
          <Dialog>
            <DialogTrigger asChild>
              <img
                src="/img/add_pic_btn.png"
                alt="add_btn_err"
                className="size-4"
                onClick={() => setIsPlusClick(true)} // 이미지 선택을 위해 모달을 열고 싶을 때 클릭 이벤트 추가
              />
            </DialogTrigger>
            {isModalOpen === true ? (
              <DialogContent className="flex h-1/2 flex-col items-center justify-center space-y-6">
                <img src={url} className="h-2/3 w-2/3 object-scale-down" />
                <div className="flex space-x-5">
                  <DialogClose asChild>
                    <Button type="button" onClick={() => handleModalClose(false)}>
                      취소
                    </Button>
                  </DialogClose>
                  <DialogClose asChild>
                    <Button type="button" onClick={() => handleModalClose(true)}>
                      올리기
                    </Button>
                  </DialogClose>
                </div>
                {/* </DialogFooter> */}
              </DialogContent>
            ) : null}
          </Dialog>
        </div>
      </>
    );
  }
  
  export default ChatImageDialog;
  