import {useNavigate} from "react-router-dom";
import {useAuthStore} from "@/stores/auth.ts";
import {useTokenStore} from "@/stores/token.ts";
import {useLocalAxios} from "@/utils/axios.ts";
import {useEffect} from "react";
import {MemberLoginResponse} from "@/types/Member.ts";

export const GoogleAuthCallback = () => {
    const navigate = useNavigate();
    const authStore = useAuthStore();
    const tokenStore = useTokenStore();
    const localAxios = useLocalAxios(false);

    useEffect(() => {
        const code = new URL(document.location.toString()).searchParams.get("code");
        console.log(code);
        localAxios
            .post<MemberLoginResponse>("/member/login/google", null, {
                params: { code },
            })
            .then((response) => {
                tokenStore.setAccessToken(response.data.accessToken);
                authStore.setAuth(response.data.member);
                navigate("/");
            });
    }, []);

    return <></>;
};