import {
    Drawer,
    DrawerClose,
    DrawerContent,
    DrawerDescription,
    DrawerFooter,
    DrawerHeader,
    DrawerTitle,
    DrawerTrigger,
} from "@/components/ui/drawer"
import { Button } from "./ui/button";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { NotificationResponse } from "@/types/Notification";
import { useEffect, useRef, useState } from "react";
import { UnitFind } from "@/types/Unit";
interface NotificationDetailProps {
    notification: NotificationResponse;
}
function NotificationDetail(props: NotificationDetailProps) {
    const [units, setUnits] = useState<UnitFind[]>();
    const sum = useRef(0);
    useEffect(() => {
        if (props.notification.units) {
            setUnits(props.notification.units);
            props.notification.units
        }
    }, [])
    return (
        <>
            <Card className="font-[Gothic] bg-white border border-gray-200 shadow-md rounded-md">
                <CardHeader className="bg-blue1 text-white rounded-t-md">
                    <CardTitle className="flex items-center">
                        {/* 포인트 색상 설정 */}
                        <div className="bg-yellow-500 rounded-full w-6 h-6 flex items-center justify-center mr-2">
                            <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" viewBox="0 0 20 20" fill="currentColor">
                                <path fillRule="evenodd" d="M10 2a1 1 0 011 1v7h4a1 1 0 010 2h-5v1a1 1 0 01-2 0V12H5a1 1 0 010-2h4V3a1 1 0 011-1z" clipRule="evenodd" />
                                <path fillRule="evenodd" d="M3 8a1 1 0 011-1h8V5a1 1 0 012 0v2h3a1 1 0 110 2h-3v2a1 1 0 01-2 0V9H4a1 1 0 01-1-1z" clipRule="evenodd" />
                            </svg>
                        </div>
                        {/* 내용 */}
                        <p>
                            {props.notification?.content}
                        </p>
                    </CardTitle>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-row justify-around">
                        {props.notification.units && props.notification.units.map((unit, index) => (
                            <div key={index} className="border border-gray-200 rounded-md mt-6">
                                <img
                                    src={unit.unitImages[0] ? unit.unitImages[0] : '../assets/images/case.png'}
                                    alt="Unit Image"
                                    className="w-full h-auto object-cover rounded-t-md"
                                />
                                <div className="p-4">
                                    <div>주인: {unit.nickname}</div>
                                    <div>연식: {unit.age}</div>
                                    <div>신뢰도: {unit.score}</div>
                                </div>
                            </div>
                        ))}
                    </div>
                </CardContent>
            </Card>

        </>
    )
}


export default NotificationDetail;