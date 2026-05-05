import React, { useState, useEffect } from 'react';
import { Activity, Ghost, Zap, DollarSign, RefreshCw } from 'lucide-react';
import './App.css';

function App() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  // 1. Fetch data from your Spring Boot Backend
  const fetchData = () => {
    setLoading(true);
    fetch(`${import.meta.env.VITE_API_URL}/api/v1/monitor/routes`)
      .then((res) => res.json())
      .then((json) => {
        setData(json);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Backend not reachable:", err);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  const zombies = data.filter(r => r.zombie).length;
  const savings = zombies * 15; // Assuming $15/mo saving per route

  return (
    <div className="min-h-screen bg-slate-50 p-8 text-slate-900">
      {/* Header */}
      <header className="mb-10 flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight text-slate-800">EffectiveApi Dashboard</h1>
          <p className="text-slate-500">Monitoring Secaucus-based infrastructure clusters</p>
        </div>
        <button
          onClick={fetchData}
          className="flex items-center gap-2 rounded-lg bg-indigo-600 px-4 py-2 text-white hover:bg-indigo-700 transition"
        >
          <RefreshCw size={18} className={loading ? "animate-spin" : ""} />
          Refresh Data
        </button>
      </header>

      {/* Stats Grid */}
      <div className="mb-10 grid grid-cols-1 gap-6 md:grid-cols-4">
        <StatCard icon={<Activity className="text-indigo-600" />} label="Total Endpoints" value={data.length} />
        <StatCard icon={<Ghost className="text-rose-600" />} label="Zombies Found" value={zombies} />
        <StatCard icon={<Zap className="text-amber-500" />} label="System Latency" value="124ms" />
        <StatCard icon={<DollarSign className="text-emerald-600" />} label="Estimated Savings" value={`$${savings}`} />
      </div>

      {/* Main Table */}
      <div className="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="bg-slate-50 border-b border-slate-200">
              <th className="p-4 font-semibold text-slate-700">Method</th>
              <th className="p-4 font-semibold text-slate-700">Endpoint Path</th>
              <th className="p-4 font-semibold text-slate-700">Last Seen</th>
              <th className="p-4 font-semibold text-slate-700">Avg. Latency</th>
              <th className="p-4 font-semibold text-slate-700">Status</th>
            </tr>
          </thead>
          <tbody>
            {data.length > 0 ? data.map((route) => (
              <tr key={route.id} className="border-b border-slate-100 hover:bg-slate-50 transition">
                <td className="p-4">
                  <span className="rounded bg-indigo-50 px-2 py-1 text-xs font-bold text-indigo-700 uppercase">
                    {route.method}
                  </span>
                </td>
                <td className="p-4 font-mono text-sm text-slate-600">{route.path}</td>
                <td className="p-4 text-sm text-slate-500">
                  {new Date(route.lastAccessed).toLocaleDateString()}
                </td>
                <td className="p-4 text-sm font-medium">{route.avgLatencyMs}ms</td>
                <td className="p-4">
                  {route.zombie ? (
                    <span className="flex items-center gap-1 text-sm font-bold text-rose-600">
                      <Ghost size={14} /> Zombie
                    </span>
                  ) : (
                    <span className="text-sm font-medium text-emerald-600">Healthy</span>
                  )}
                </td>
              </tr>
            )) : (
              <tr>
                <td colSpan="5" className="p-10 text-center text-slate-400">
                  No data found. Send a test request to your backend to populate this list.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

// Reusable Stat Card Component
const StatCard = ({ icon, label, value }) => (
  <div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
    <div className="mb-3 flex items-center gap-2">
      {icon}
      <span className="text-sm font-medium text-slate-500 uppercase tracking-wider">{label}</span>
    </div>
    <div className="text-3xl font-bold text-slate-800">{value}</div>
  </div>
);

export default App;