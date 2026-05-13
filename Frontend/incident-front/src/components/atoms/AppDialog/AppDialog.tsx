import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { type ComponentProps } from "react";

type AppDialogProps = ComponentProps<typeof Dialog> & {
  title: string;
  description?: string;
  footer?: React.ReactNode;
  showCloseButton?: boolean;
  children?: React.ReactNode;
};

export function AppDialog({
  title,
  description,
  footer,
  showCloseButton = true,
  children,
  ...props
}: AppDialogProps) {
  return (
    <Dialog {...props}>
      <DialogContent showCloseButton={showCloseButton}>
        <DialogHeader>
          <DialogTitle>{title}</DialogTitle>
          {description && <DialogDescription>{description}</DialogDescription>}
        </DialogHeader>

        {children}

        {footer && <DialogFooter>{footer}</DialogFooter>}
      </DialogContent>
    </Dialog>
  );
}
