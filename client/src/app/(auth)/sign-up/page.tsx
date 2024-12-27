import { Separator } from "@/components/ui/separator";

type Props = {};

const SignUpPage = (props: Props) => {
  return (
    <>
      <h5 className="font-bold text-themeTextWhite text-3xl">Sign Up</h5>
      <p className="text-themeTextGray leading-tight">
        Network with people from around the world, join groups, create your own,
        watch courses and become the best version of yourself.
      </p>
      {/* WIP: Add signup form */}
      {/* <SignUpForm /> */}
      <div className="my-10 w-full relative">
        <div className="bg-black p-3 absolute text-themeTextGray text-xs top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
          OR CONTINUE WITH
        </div>
        <Separator orientation="horizontal" className="bg-themeGray" />
      </div>
      {/* <GoogleAuthButton method="signup" /> */}
    </>
  );
};

export default SignUpPage;