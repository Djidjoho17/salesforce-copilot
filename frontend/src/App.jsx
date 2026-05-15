import { useState, useEffect } from 'react';
import axios from 'axios';

const STAGES = [
  'Prospecting',
  'Qualification',
  'Proposal',
  'Negotiation',
  'Closed Won'
];

const STAGE_COLORS = {
  'Prospecting':   'bg-gray-100 border-gray-300',
  'Qualification': 'bg-blue-50 border-blue-300',
  'Proposal':      'bg-yellow-50 border-yellow-300',
  'Negotiation':   'bg-orange-50 border-orange-300',
  'Closed Won':    'bg-green-50 border-green-300',
};

export default function App() {
  const [opportunities, setOpportunities] = useState([]);
  const [aiQuestion, setAiQuestion]       = useState('');
  const [aiResponse, setAiResponse]       = useState('');
  const [aiLoading, setAiLoading]         = useState(false);
  const [loaded, setLoaded]               = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    axios.get('http://localhost:8080/api/opportunities')
      .then(res => {
        setOpportunities(res.data);
        setLoaded(true);
      })
      .catch(err => {
  console.error('Backend error:', err);
  setError('Cannot connect to backend: ' + err.message);
  setLoaded(true);
});
  }, []);

  const dealsByStage = (stage) =>
    opportunities.filter(o => o.stage === stage);

  const totalPipeline = opportunities
    .filter(o => o.stage !== 'Closed Won')
    .reduce((sum, o) => sum + (o.amount || 0), 0);

  const closedWon = opportunities
    .filter(o => o.stage === 'Closed Won')
    .reduce((sum, o) => sum + (o.amount || 0), 0);

  const handleAskAI = async () => {
    if (!aiQuestion.trim()) return;
    setAiLoading(true);
    setAiResponse('');
    try {
const res = await axios.post('http://localhost:8080/api/ai/analyze', {        question: aiQuestion
      });
      setAiResponse(res.data.response);
    } catch (err) {
      setAiResponse('Error reaching AI.');
    } finally {
      setAiLoading(false);
    }
  };

  if (!loaded) {
    return (
      <div style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100vh',
        fontFamily: 'sans-serif',
        color: '#666'
      }}>
        Loading pipeline data...
      </div>
    );
  }
  if (error) {
  return (
    <div style={{
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      height: '100vh',
      fontFamily: 'sans-serif',
      color: 'red',
      padding: '40px',
      textAlign: 'center'
    }}>
      {error}
    </div>
  );
}

  return (
    <div style={{ minHeight: '100vh', backgroundColor: '#f9fafb',
                  fontFamily: 'sans-serif' }}>

      {/* Header */}
      <div style={{ backgroundColor: 'white', borderBottom: '1px solid #e5e7eb',
                    padding: '16px 24px' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between',
                      alignItems: 'center' }}>
          <div>
            <h1 style={{ margin: 0, fontSize: '20px', fontWeight: '600',
                         color: '#111827' }}>
              Sales Intelligence Copilot
            </h1>
            <p style={{ margin: 0, fontSize: '13px', color: '#6b7280' }}>
              AI-powered pipeline management
            </p>
          </div>
          <div style={{ display: 'flex', gap: '32px' }}>
            <div style={{ textAlign: 'right' }}>
              <p style={{ margin: 0, fontSize: '12px', color: '#6b7280' }}>
                Open Pipeline
              </p>
              <p style={{ margin: 0, fontSize: '18px', fontWeight: '600',
                           color: '#2563eb' }}>
                ${totalPipeline.toLocaleString()}
              </p>
            </div>
            <div style={{ textAlign: 'right' }}>
              <p style={{ margin: 0, fontSize: '12px', color: '#6b7280' }}>
                Closed Won
              </p>
              <p style={{ margin: 0, fontSize: '18px', fontWeight: '600',
                           color: '#16a34a' }}>
                ${closedWon.toLocaleString()}
              </p>
            </div>
            <div style={{ textAlign: 'right' }}>
              <p style={{ margin: 0, fontSize: '12px', color: '#6b7280' }}>
                Total Deals
              </p>
              <p style={{ margin: 0, fontSize: '18px', fontWeight: '600',
                           color: '#111827' }}>
                {opportunities.length}
              </p>
            </div>
          </div>
        </div>
      </div>

      <div style={{ padding: '24px' }}>

        {/* AI Panel */}
        <div style={{ backgroundColor: 'white', border: '1px solid #e5e7eb',
                      borderRadius: '12px', padding: '20px',
                      marginBottom: '24px' }}>
          <div style={{ display: 'flex', alignItems: 'center',
                        gap: '8px', marginBottom: '12px' }}>
            <div style={{ width: '8px', height: '8px', borderRadius: '50%',
                          backgroundColor: '#22c55e' }}></div>
            <h2 style={{ margin: 0, fontSize: '14px', fontWeight: '600',
                         color: '#374151' }}>
              AI Copilot — Ask anything about your pipeline
            </h2>
          </div>

          {/* Quick question buttons */}
          <div style={{ display: 'flex', gap: '8px',
                        marginBottom: '12px', flexWrap: 'wrap' }}>
            {[
  'Which deals are at risk?',
  'What should I focus on this week?',
  'Summarize my pipeline',
  'Draft a follow-up email for Acme',
  'Which deal should I prioritize?',
  'What is my pipeline forecast?',
].map(q => (
              <button
                key={q}
                onClick={() => setAiQuestion(q)}
                style={{ fontSize: '12px', backgroundColor: '#f3f4f6',
                         border: 'none', color: '#4b5563', padding: '6px 12px',
                         borderRadius: '999px', cursor: 'pointer' }}
              >
                {q}
              </button>
            ))}
          </div>

          {/* Input */}
          <div style={{ display: 'flex', gap: '12px' }}>
            <input
              style={{ flex: 1, border: '1px solid #e5e7eb',
                       borderRadius: '8px', padding: '8px 16px',
                       fontSize: '14px', outline: 'none' }}
              placeholder="Ask your AI copilot a question..."
              value={aiQuestion}
              onChange={e => setAiQuestion(e.target.value)}
              onKeyDown={e => e.key === 'Enter' && handleAskAI()}
            />
            <button
              onClick={handleAskAI}
              disabled={aiLoading}
              style={{ backgroundColor: '#2563eb', color: 'white',
                       padding: '8px 20px', borderRadius: '8px',
                       border: 'none', fontSize: '14px', fontWeight: '500',
                       cursor: aiLoading ? 'not-allowed' : 'pointer',
                       opacity: aiLoading ? 0.6 : 1 }}
            >
              {aiLoading ? 'Thinking...' : 'Ask →'}
            </button>
          </div>

          {/* AI Response */}
          {aiResponse && (
            <div style={{ marginTop: '16px', backgroundColor: '#eff6ff',
                          border: '1px solid #dbeafe', borderRadius: '8px',
                          padding: '16px' }}>
              <p style={{ margin: '0 0 8px 0', fontSize: '12px',
                          fontWeight: '600', color: '#2563eb' }}>
                AI Analysis
              </p>
              {aiResponse.split('\n').filter(Boolean).map((line, i) => (
                <p key={i} style={{ margin: '4px 0', fontSize: '14px',
                                    color: '#1e3a8a', lineHeight: '1.6' }}>
                  {line}
                </p>
              ))}
            </div>
          )}
        </div>
        {/* Metrics Dashboard */}
<div style={{
  display: 'grid',
  gridTemplateColumns: 'repeat(4, 1fr)',
  gap: '16px',
  marginBottom: '24px'
}}>

  {/* Total Pipeline Value */}
  <div style={{
    backgroundColor: 'white',
    border: '1px solid #e5e7eb',
    borderRadius: '12px',
    padding: '20px'
  }}>
    <p style={{ margin: '0 0 8px 0', fontSize: '12px',
                color: '#6b7280', fontWeight: '500' }}>
      TOTAL PIPELINE
    </p>
    <p style={{ margin: '0', fontSize: '28px',
                fontWeight: '700', color: '#2563eb' }}>
      ${(totalPipeline + closedWon).toLocaleString()}
    </p>
    <p style={{ margin: '4px 0 0 0', fontSize: '12px',
                color: '#6b7280' }}>
      across {opportunities.length} deals
    </p>
  </div>

  {/* Win Rate */}
  <div style={{
    backgroundColor: 'white',
    border: '1px solid #e5e7eb',
    borderRadius: '12px',
    padding: '20px'
  }}>
    <p style={{ margin: '0 0 8px 0', fontSize: '12px',
                color: '#6b7280', fontWeight: '500' }}>
      WIN RATE
    </p>
    <p style={{ margin: '0', fontSize: '28px',
                fontWeight: '700', color: '#16a34a' }}>
      {opportunities.length > 0
        ? Math.round((opportunities.filter(o =>
            o.stage === 'Closed Won').length /
            opportunities.length) * 100)
        : 0}%
    </p>
    <p style={{ margin: '4px 0 0 0', fontSize: '12px',
                color: '#6b7280' }}>
      {opportunities.filter(o =>
        o.stage === 'Closed Won').length} won of {opportunities.length}
    </p>
  </div>

  {/* Average Deal Size */}
  <div style={{
    backgroundColor: 'white',
    border: '1px solid #e5e7eb',
    borderRadius: '12px',
    padding: '20px'
  }}>
    <p style={{ margin: '0 0 8px 0', fontSize: '12px',
                color: '#6b7280', fontWeight: '500' }}>
      AVG DEAL SIZE
    </p>
    <p style={{ margin: '0', fontSize: '28px',
                fontWeight: '700', color: '#7c3aed' }}>
      ${opportunities.length > 0
        ? Math.round(opportunities.reduce((sum, o) =>
            sum + (o.amount || 0), 0) /
            opportunities.length).toLocaleString()
        : 0}
    </p>
    <p style={{ margin: '4px 0 0 0', fontSize: '12px',
                color: '#6b7280' }}>
      average across all deals
    </p>
  </div>

  {/* Pipeline Velocity */}
  <div style={{
    backgroundColor: 'white',
    border: '1px solid #e5e7eb',
    borderRadius: '12px',
    padding: '20px'
  }}>
    <p style={{ margin: '0 0 8px 0', fontSize: '12px',
                color: '#6b7280', fontWeight: '500' }}>
      HIGH PRIORITY
    </p>
    <p style={{ margin: '0', fontSize: '28px',
                fontWeight: '700', color: '#dc2626' }}>
      {opportunities.filter(o =>
        o.probability >= 70 &&
        o.stage !== 'Closed Won').length}
    </p>
    <p style={{ margin: '4px 0 0 0', fontSize: '12px',
                color: '#6b7280' }}>
      deals above 70% probability
    </p>
  </div>

</div>

        {/* Kanban Board */}
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(5, 1fr)',
                      gap: '16px' }}>
          {STAGES.map(stage => (
            <div key={stage} style={{
              backgroundColor: stage === 'Prospecting' ? '#f3f4f6' :
                               stage === 'Qualification' ? '#eff6ff' :
                               stage === 'Proposal' ? '#fefce8' :
                               stage === 'Negotiation' ? '#fff7ed' :
                               '#f0fdf4',
              border: `1px solid ${
                               stage === 'Prospecting' ? '#d1d5db' :
                               stage === 'Qualification' ? '#93c5fd' :
                               stage === 'Proposal' ? '#fde047' :
                               stage === 'Negotiation' ? '#fdba74' :
                               '#86efac'}`,
              borderRadius: '12px',
              padding: '12px'
            }}>
              {/* Column header */}
              <div style={{ display: 'flex', justifyContent: 'space-between',
                            alignItems: 'center', marginBottom: '12px' }}>
                <h3 style={{ margin: 0, fontSize: '11px', fontWeight: '600',
                             color: '#4b5563', textTransform: 'uppercase',
                             letterSpacing: '0.05em' }}>
                  {stage}
                </h3>
                <span style={{ fontSize: '11px', backgroundColor: 'white',
                               border: '1px solid #e5e7eb', color: '#6b7280',
                               borderRadius: '999px', padding: '2px 8px' }}>
                  {dealsByStage(stage).length}
                </span>
              </div>

              {/* Deal cards */}
              <div style={{ display: 'flex', flexDirection: 'column',
                            gap: '8px' }}>
                {dealsByStage(stage).length === 0 ? (
                  <p style={{ fontSize: '12px', color: '#9ca3af',
                              textAlign: 'center', padding: '16px 0',
                              margin: 0 }}>
                    No deals
                  </p>
                ) : (
                  dealsByStage(stage).map(deal => (
                    <div key={deal.id} style={{
                      backgroundColor: 'white',
                      border: '1px solid #e5e7eb',
                      borderRadius: '8px',
                      padding: '12px',
                      cursor: 'pointer'
                    }}>
                      <p style={{ margin: '0 0 2px 0', fontSize: '13px',
                                  fontWeight: '500', color: '#111827',
                                  lineHeight: '1.3' }}>
                        {deal.name}
                      </p>
                      <p style={{ margin: '0 0 8px 0', fontSize: '11px',
                                  color: '#9ca3af' }}>
                        {deal.account?.name}
                      </p>
                      <p style={{ margin: '0 0 8px 0', fontSize: '14px',
                                  fontWeight: '600', color: '#374151' }}>
                        ${deal.amount?.toLocaleString()}
                      </p>
                      <div style={{ display: 'flex', justifyContent: 'space-between',
                                    alignItems: 'center' }}>
                        <span style={{ fontSize: '11px', color: '#9ca3af' }}>
                          {deal.closeDate}
                        </span>
                        <span style={{
                          fontSize: '11px', fontWeight: '500',
                          padding: '2px 6px', borderRadius: '4px',
                          backgroundColor: deal.probability >= 70 ? '#dcfce7' :
                                           deal.probability >= 40 ? '#fef9c3' :
                                           '#fee2e2',
                          color: deal.probability >= 70 ? '#16a34a' :
                                 deal.probability >= 40 ? '#ca8a04' :
                                 '#dc2626'
                        }}>
                          {deal.probability}%
                        </span>
                      </div>
                    </div>
                  ))
                )}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}