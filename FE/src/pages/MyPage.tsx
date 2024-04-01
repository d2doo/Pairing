import InfoUser from "@/components/InfoUser";
import InfoSale from "@/components/InfoSale";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
function MyPage() {
  return (
    <>
      <Tabs defaultValue="saleInfo" className="m-0 w-full p-0 font-Gothic">
        <TabsList className="m-0 grid w-full grid-cols-2  border-b border-b-black1 p-0 text-black1">
          <TabsTrigger
            value="saleInfo"
            className="h-full
                 data-[state=active]:border-b-2 data-[state=active]:border-b-black1 data-[state=active]:text-black1"
          >
            거래 정보
          </TabsTrigger>
          <TabsTrigger
            value="refactor"
            className="h-full
                 data-[state=active]:border-b-2 data-[state=active]:border-b-black1 data-[state=active]:text-black1"
          >
            내 정보
          </TabsTrigger>
        </TabsList>
        <TabsContent value="saleInfo">
          <Tabs defaultValue="전체" className="w-full">
            <InfoSale />
          </Tabs>
        </TabsContent>
        <TabsContent value="refactor">
          <Tabs defaultValue="전체" className="w-full">
            <InfoUser />
          </Tabs>
        </TabsContent>
      </Tabs>
    </>
  );
}

export default MyPage;
