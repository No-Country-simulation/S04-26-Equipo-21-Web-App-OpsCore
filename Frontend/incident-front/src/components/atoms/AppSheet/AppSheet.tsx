import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetFooter,
  SheetHeader,
  SheetTitle,
} from "@/components/ui/sheet";
import { type ComponentProps, type ReactNode } from "react";

type AppSheetProps = ComponentProps<typeof Sheet> & {
  title: string;
  description?: string;
  side?: "top" | "right" | "bottom" | "left";
  showCloseButton?: boolean;
  footer?: ReactNode;
  children?: ReactNode;
};

export function AppSheet({
  title,
  description,
  side = "bottom",
  showCloseButton = true,
  footer,
  children,
  ...props
}: AppSheetProps) {
  return (
    <Sheet {...props}>
      <SheetContent side={side} showCloseButton={showCloseButton}>
        <SheetHeader>
          <SheetTitle>{title}</SheetTitle>
          {description && <SheetDescription>{description}</SheetDescription>}
        </SheetHeader>

        {children}

        {footer && <SheetFooter>{footer}</SheetFooter>}
      </SheetContent>
    </Sheet>
  );
}
