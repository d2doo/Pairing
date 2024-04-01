import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useAuthStore } from "@/stores/auth";
import { useEffect, useState } from "react";
import { MemberResponse, MemberUpdateRequest } from "@/types/Member";
import Postcode from "./DaumPost";
import { DefaultImageUploader } from "./DefaultImageUploader";
import { AxiosResponse } from "axios";
import { useLocalAxios } from "@/utils/axios";
import Swal from "sweetalert2";

function InfoUser() {
  const auth = useAuthStore();
  const localAxios = useLocalAxios();
  const [userInfo, setUserInfo] = useState<MemberUpdateRequest>({
    nickname: auth.nickname ? auth.nickname : "비회원",
    profileImage: auth.profileImage ? auth.profileImage : "/img/extra.png",
    address: "",
    phoneNumber: "",
  });
  const onChangedUpdateRequest = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserInfo((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }));
  };
  const onChangedAddress = (value: string) => {
    setUserInfo((prevState) => ({
      ...prevState,
      address: value,
    }));
  };
  const onChangedImage = (value: string) => {
    setUserInfo((prevState) => ({
      ...prevState,
      profileImage: value ? value : "/img/extra.png",
    }));
  };

  useEffect(() => {
    console.log(userInfo);
  }, [userInfo]);

  const onImageUploadComplete = (response: AxiosResponse) => {
    const profileImage: string = response.data.imgUrl;
    onChangedImage(profileImage);
  };

  const UpdateMember = () => {
    if (auth.nickname) {
      Swal.fire({
        title: "회원 정보를 변경하시겠습니까?",
        showDenyButton: true,
        confirmButtonText: "Save",
        denyButtonText: `Don't save`,
      }).then((result) => {
        if (result.isConfirmed) {
          localAxios
            .put<MemberResponse>("/member", JSON.stringify(userInfo))
            .then((res) => {
              console.log("res", res.data);
              Swal.fire("회원 정보가 변경되었습니다.");
              auth.setAuthByChange(res.data);
            });
        }
      });
    }
  };

  return (
    <>
      <div className="mb-14 flex flex-col items-center border-y border-gray1 font-GothicLight text-sm">
        <div className="w-50 mt-4 flex items-center space-x-4 text-xs">
          <DefaultImageUploader
            selectedUrl={auth.profileImage}
            className="h-32 w-32"
            onUploadComplete={onImageUploadComplete}
          />
          <div className="text-md h-full">
            <p>{auth.nickname}</p>
            <div className="flex">
              <img
                src="/img/platinum.png"
                alt="pl_err"
                className="size-4 object-cover"
              />
              <p>{auth.score}</p>
            </div>
          </div>
        </div>
        <div className="mt-4 flex w-full px-4">
          <div className="w-full">
            <p className="flex items-center font-Gothic text-xxs">닉네임</p>
            <div className="flex h-9 border-b-2">
              <Input
                placeholder="이곳에 입력하세요."
                className="test-xs p-1"
                name="nickname"
                value={userInfo.nickname}
                onChange={onChangedUpdateRequest}
              ></Input>
              {/* <Button className="my-1 border border-gray1 bg-transparent">
                수정
              </Button> */}
            </div>
          </div>
        </div>
        <div className="mt-4 flex w-full px-4">
          <div className="w-full">
            <p className="flex items-center font-Gothic text-xxs">연락처</p>
            <div className="flex h-9 border-b-2">
              <Input
                placeholder="이곳에 입력하세요."
                className="test-xs p-1"
                value={userInfo.phoneNumber}
                name="phoneNumber"
                onChange={onChangedUpdateRequest}
              ></Input>
              {/* <Button className="my-1 border border-gray1 bg-transparent">
                수정
              </Button> */}
            </div>
          </div>
        </div>
        {/* <div className="mt-4 flex w-full px-4">
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
        </div> */}
        <div className="mt-4 flex w-full px-4">
          <div className="w-full">
            <Postcode onChanged={onChangedAddress} />
          </div>
        </div>

        {/* 다음 주소 가져오면 삭제할 부분 시작 */}
        <div className="w-full px-4">
          <div className="mt-2 w-full">
            <Button
              className="my-1 h-full w-full border border-gray1 bg-transparent"
              onClick={UpdateMember}
            >
              수정
            </Button>
          </div>
        </div>
        {/* 삭제할 부분 끝 */}
        <div className="my-4 flex w-full justify-start px-4 underline-offset-4">
          <p className="text-left text-xxs text-gray1 underline">탈퇴하기</p>
        </div>
      </div>
    </>
  );
}

export default InfoUser;
