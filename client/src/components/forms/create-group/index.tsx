import { StripeElements } from "@/components/global/stripe/element";
import PaymentForm from "./payment-from";

type Props = {
  userId: string;
  affiliate: boolean;
  stripeId?: string;
};

const CreateGroup = ({ userId, affiliate, stripeId }: Props) => {
  return (
    <StripeElements>
      <PaymentForm userId={userId} affiliate={affiliate} stripeId={stripeId} />
    </StripeElements>
  );
};

export default CreateGroup;
