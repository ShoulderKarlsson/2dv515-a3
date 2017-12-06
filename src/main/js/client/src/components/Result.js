import * as React from 'react'

export const Result = ({score, p: {url}}) => {
	return (
		<div className="result-container">
			<p>score: {score}</p>
			<a href={`http://wikipedia.com/${url}`}>{url}</a>
		</div>
	)
}
