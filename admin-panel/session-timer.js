// Client-side session timer for admin panel (web)
// Place this file in admin-panel/ and import/use from your frontend bundle.

export function startSessionTimer(expiresAt, onExpire, onTick) {
  const intervalId = setInterval(() => {
    const remaining = expiresAt - Date.now();
    if (remaining <= 0) {
      clearInterval(intervalId);
      onTick("Expired");
      onExpire();
    } else {
      onTick(formatDuration(remaining));
    }
  }, 1000);
  return () => clearInterval(intervalId);
}

function formatDuration(ms) {
  const s = Math.floor(ms / 1000);
  const h = Math.floor(s / 3600);
  const m = Math.floor((s % 3600) / 60);
  const sec = s % 60;
  if (h > 0) return `${h}h ${m}m ${sec}s`;
  if (m > 0) return `${m}m ${sec}s`;
  return `${sec}s`;
}
