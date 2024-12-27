"use client";

import { SimpleModal } from "@/components/global/simple-modal";
import { useChannelPage } from "@/hooks/channel";
import { Card, CardContent, CardDescription } from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { PostContent } from "@/components/global/post-content";

type Props = { userImage: string; channelid: string; username: string };

const CreateNewPost = ({ channelid, userImage, username }: Props) => {
  const { data, mutation } = useChannelPage(channelid);
  const { name } = data?.data?.channel;
  return (
    <>
      <SimpleModal
        trigger={
          <span>
            <Card className="border-themeGray cursor-pointer first-letter:rounded-2xl overflow-hidden">
              <CardContent className="p-3 bg-[#1A1A1D] flex gap-x-6 items-center ">
                <Avatar className="cursor-pointer">
                  <AvatarImage src={userImage} alt="user" />
                  <AvatarFallback>U</AvatarFallback>
                </Avatar>
                <CardDescription className="text-themeTextGray">
                  Type / to add elements to your post...
                </CardDescription>
              </CardContent>
            </Card>
          </span>
        }
      >
        <div className="flex gap-x-3">
          <Avatar className="cursor-pointer">
            <AvatarImage src={userImage} alt="user" />
            <AvatarFallback>U</AvatarFallback>
          </Avatar>
          <div className="flex flex-col">
            <p className="text-themeTextGray text-sm capitalize">{username}</p>
            <p className="text-sm captialize text-themeTextGray">
              Posting in{" "}
              <span className="font-bold capitalize text-themeTextWhite">
                {name}
              </span>
            </p>
          </div>
        </div>
        <PostContent channelid={channelid} />
      </SimpleModal>

      {mutation.length > 0 &&
        mutation[0].status === "pending" &&
        mutation[0].state && <></>}
    </>
  );
};

export default CreateNewPost;
