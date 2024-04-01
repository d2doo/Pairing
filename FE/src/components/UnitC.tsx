import { Badge } from "./ui/badge";
// import { useEffect } from "react";
interface UnitCProps {
  unitId: number;
  images: string[];
  unitDescription: string;
  price: number;
  category: string;
  age: number;
  handler: (name: UnitCProps) => void;
  idx: number;
}

function UnitC(props: UnitCProps) {
  return (
    <>
      <div
        className="mx-8 box-border flex justify-between border-2 border-solid border-black1 p-4"
        onClick={() => {
          props.handler(props);
        }}
      >
        <img
          src={props.images[0]}
          alt="없음"
          className="size-20 object-cover"
        />
        <div className="flex h-20 w-44 flex-col justify-center px-2 font-GothicLight">
          <div className="flex text-xxs">
            <Badge>{props.category}</Badge>
          </div>
          <div className="my-1 text-xs">{props.unitDescription}</div>
          <div className="my-1 text-xs">연식: {props.age}</div>
          <div className="my=1 text-xs">₩ {props.price.toLocaleString()}</div>
        </div>
      </div>
    </>
  );
}

export default UnitC;
