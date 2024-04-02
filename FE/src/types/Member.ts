interface MemberResponse extends MemberSummaryResponse {
  email: string;
  provider: string;
  address: string;
  phoneNumber: string;
}

interface MemberLoginResponse {
  accessToken: string;
  member: MemberSummaryResponse;
}

interface MemberSummaryResponse {
  memberId: string;
  nickname: string;
  profileImage: string;
  score: number;
}

interface MemberUpdateRequest {
  nickname: string;
  profileImage: string;
  address: string;
  phoneNumber: string;
}

export type {
  MemberResponse,
  MemberLoginResponse,
  MemberSummaryResponse,
  MemberUpdateRequest,
};
