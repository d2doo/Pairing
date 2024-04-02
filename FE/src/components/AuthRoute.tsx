import {useAuthStore} from "@/stores/auth.ts";
import {Navigate, Outlet} from "react-router-dom";

export const AuthRoute = () => {
    const authStore = useAuthStore();

    return (
        authStore.memberId
            ? <Outlet />
            : <Navigate to="/login" />
    );
};