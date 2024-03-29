import React, { useEffect, useState } from "react";
import { useDaumPostcodePopup } from "react-daum-postcode";
import { Button } from "./ui/button";
import { Input } from "./ui/input";

interface DaumPostcodeData {
  address: string;
  addressType: string;
  bname: string;
  buildingName: string;
}

interface DaumPostcodeOptions {
  onComplete: (data: DaumPostcodeData) => void;
  onChanged: (value: string) => void;
}
interface PostcodeProps {
  onChanged: (value: string) => void;
}

const Postcode: React.FC<PostcodeProps> = ({ onChanged }) => {
  const scriptUrl =
    "https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
  const open = useDaumPostcodePopup(scriptUrl);
  const [value, setValue] = useState("");
  const handleInputChange = () => {};
  useEffect(() => {
    onChanged(value);
  }, [value]);
  const handleComplete = (data: DaumPostcodeData) => {
    let fullAddress = data.address;
    let extraAddress = "";

    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddress += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddress +=
          extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
    }
    setValue(fullAddress);
  };

  const handleClick = () => {
    const options: DaumPostcodeOptions = {
      onComplete: handleComplete,
      onChanged: onChanged,
    };
    open(options);
  };

  return (
    <>
      <p className="flex items-center font-Gothic text-xxs">주소</p>
      <div className="flex">
        <Input
          placeholder="주소"
          className="test-xs m-1 w-full p-1"
          name="address"
          value={value}
          onChange={handleInputChange}
        ></Input>
        <Button
          className="my-1 h-full border border-gray1 bg-transparent"
          onClick={handleClick}
        >
          주소 검색
        </Button>
      </div>
    </>
  );
};

export default Postcode;
