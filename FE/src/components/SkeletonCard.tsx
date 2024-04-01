import { Skeleton } from "@/components/ui/skeleton";

function SkeletonCard(props: { size: number }) {
  const rendering = () => {
    const result = [];
    for (let i = 0; i < props.size; i++) {
      result.push(
        <div key={i} className="flex flex-col space-y-3">
          <Skeleton className="h-[125px] w-32 rounded-xl" />
          <div className="space-y-2">
            <Skeleton className="h-4 w-32" />
            <Skeleton className="h-4 w-32" />
          </div>
        </div>,
      );
    }
    return result;
  };

  return (
    <div className="flex flex-wrap px-7 ">
      <div className="grid grid-cols-2 gap-x-10 gap-y-7 transition delay-700">
        {rendering()}
      </div>
    </div>
  );
}

export default SkeletonCard;
