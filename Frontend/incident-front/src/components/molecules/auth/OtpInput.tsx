import { useRef, type KeyboardEvent, type ClipboardEvent } from "react";
import { AppLabel } from "@/components/atoms";
import { OTP_LENGTH } from "@/constants";
import type { OtpInputProps } from "../types";

export function OtpInput({
  label = "Codigo de verificación",
  value,
  onChange,
  errorMessage,
  disabled,
}: OtpInputProps) {
  const refs = useRef<Array<HTMLInputElement | null>>([]);
  const digits = Array.from({ length: OTP_LENGTH }, (_, i) => value[i] ?? "");

  const focus = (index: number) => refs.current[index]?.focus();

  const handleChange = (index: number, raw: string) => {
    const digit = raw.replace(/\D/g, "").slice(-1);
    const next = digits.map((d, i) => (i === index ? digit : d));
    onChange(next.join("").trimEnd());
    if (digit && index < OTP_LENGTH - 1) focus(index + 1);
  };

  const handleKeyDown = (index: number, e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Backspace") {
      if (digits[index]) {
        const next = digits.map((d, i) => (i === index ? "" : d));
        onChange(next.join("").trimEnd());
      } else if (index > 0) {
        focus(index - 1);
      }
    }
    if (e.key === "ArrowLeft" && index > 0) focus(index - 1);
    if (e.key === "ArrowRight" && index < OTP_LENGTH - 1) focus(index + 1);
  };

  const handlePaste = (e: ClipboardEvent<HTMLInputElement>) => {
    e.preventDefault();
    const pasted = e.clipboardData
      .getData("text")
      .replace(/\D/g, "")
      .slice(0, OTP_LENGTH);
    onChange(pasted);
    focus(Math.min(pasted.length, OTP_LENGTH - 1));
  };

  return (
    <div className="flex flex-col gap-1.5">
      <AppLabel>{label}</AppLabel>

      <div
        className="flex gap-2 items-center mt-2 justify-center"
        role="group"
        aria-label={label}
      >
        {digits.map((digit, index) => (
          <input
            key={index}
            ref={(el) => {
              refs.current[index] = el;
            }}
            value={digit}
            onChange={(e) => handleChange(index, e.target.value)}
            onKeyDown={(e) => handleKeyDown(index, e)}
            onPaste={handlePaste}
            onFocus={(e) => e.target.select()}
            inputMode="numeric"
            maxLength={1}
            disabled={disabled}
            aria-label={`Digit ${index + 1} of ${OTP_LENGTH}`}
            className="w-10 h-10 rounded-lg border border-black bg-white text-center text-base font-mono"
          />
        ))}
      </div>

      {errorMessage && (
        <span className="text-xs text-red-500">{errorMessage}</span>
      )}
    </div>
  );
}
