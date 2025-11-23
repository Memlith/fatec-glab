interface SuspensePageProps {
  text: string;
}

export default function SuspensePage({ text }: SuspensePageProps) {
  return (
    <div className="h-screen w-full flex items-center justify-center p-8">
      <div className="text-xl font-semibold animate-pulse">{text}</div>
    </div>
  );
}
