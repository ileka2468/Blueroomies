import { useState } from 'react';

const SliderPref = ({ min, max, step, value, onChange, label }) => (
    <div style={{ width: '100%' }}>
        <label>{label}</label>
        <input
            type="range"
            min={1}
            max={5}
            step={1}
            value={value}
            onChange={(e) => onChange(e.target.value)}
            style={{ width: '100%' }}
        />
        <div>{value}</div>
    </div>
);

export default SliderPref;
