import { onAuthenticatedUser } from "@/actions/auth";
import { isSubscribed, onGetGroupInfo } from "@/actions/groups";
import { onGetActiveSubscription } from "@/actions/payment";

import {
  HydrationBoundary,
  QueryClient,
  dehydrate,
} from "@tanstack/react-query";
import AboutGroup from "../_components/about";
import GroupSideWidget from "@/components/global/group-side-widget";

type Props = {
  params: {
    groupid: string;
  };
};

const Page = async ({ params }: Props) => {
  const query = new QueryClient();

  await query.prefetchQuery({
    queryKey: ["about-group-info"],
    queryFn: () => onGetGroupInfo(params.groupid),
  });

  await query.prefetchQuery({
    queryKey: ["active-subscription"],
    queryFn: () => onGetActiveSubscription(params.groupid),
  });

  await query.prefetchQuery({
    queryKey: ["isSubscribed"],
    queryFn: () => isSubscribed(params.groupid),
  });

  const { data: userid } = await onAuthenticatedUser();

  return (
    <HydrationBoundary state={dehydrate(query)}>
      <div className="pt-36 pb-10 container grid grid-cols-1 lg:grid-cols-3 gap-x-10">
        <div className="col-span-1 lg:col-span-2">
          <AboutGroup groupid={params.groupid} userid={userid?.id} />
        </div>
        <div className="col-span-1 relative">
          <GroupSideWidget groupid={params.groupid} userid={userid?.id} />
        </div>
      </div>
    </HydrationBoundary>
  );
};

export default Page;
