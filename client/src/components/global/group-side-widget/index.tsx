"use client";
import { JoinButton } from "@/app/(discover)/about/_components/join-button";
import { Card } from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { useGroupInfo } from "@/hooks/groups";
import { cn, truncateString } from "@/lib/utils";
import Image from "next/image";

type Props = {
  light?: boolean;
  groupid?: string;
  userid?: string;
};

const GroupSideWidget = ({ groupid, light, userid }: Props) => {
  const { group, groupOwner, subscribed } = useGroupInfo();

  return (
    <Card
      className={cn(
        "border-themeGray lg:sticky lg:top-0 mt-10 lg:mt-0 rounded-xl overflow-hidden",
        light ? "border-themeGray bg-[#1A1A1D]" : "bg-themeBlack"
      )}
    >
      <Image
        width={1920}
        height={1080}
        src={
          group?.thumbnail ||
          "https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg"
        }
        alt="thumbnail"
        className="w-full aspect-video"
      />
      <div className="flex flex-col p-5 gap-y-2">
        <h2 className="font-bold text-lg">{group?.name}</h2>
        <p className="text-sm text-themeTextGray">
          {group?.description && truncateString(group?.description)}
        </p>
      </div>
      <Separator orientation="horizontal" className="bg-themeGray" />
      {groupid && (
        <JoinButton
          groupid={groupid}
          owner={groupOwner}
          isSubscribed={subscribed}
          user={userid ? true : false}
        />
      )}
    </Card>
  );
};

export default GroupSideWidget;
